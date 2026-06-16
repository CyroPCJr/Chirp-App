package br.com.cpcjrdev.chat.data.message

import br.com.cpcjrdev.chat.data.dto.websocket.OutgoingWebSocketDto
import br.com.cpcjrdev.chat.data.dto.websocket.WebSocketMessageDto
import br.com.cpcjrdev.chat.data.mappers.toDomain
import br.com.cpcjrdev.chat.data.mappers.toEntity
import br.com.cpcjrdev.chat.data.mappers.toWebSocketDto
import br.com.cpcjrdev.chat.data.network.KtorWebSocketConnector
import br.com.cpcjrdev.chat.database.ChirpChatDatabase
import br.com.cpcjrdev.chat.domain.message.ChatMessageService
import br.com.cpcjrdev.chat.domain.message.MessageRepository
import br.com.cpcjrdev.chat.domain.models.ChatMessage
import br.com.cpcjrdev.chat.domain.models.ChatMessageDeliveryStatus
import br.com.cpcjrdev.chat.domain.models.MessageWithSender
import br.com.cpcjrdev.chat.domain.models.OutgoingNewMessage
import br.com.cpcjrdev.core.data.database.safeDatabaseUpdate
import br.com.cpcjrdev.core.domain.auth.SessionStorage
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result
import br.com.cpcjrdev.core.domain.util.onFailure
import br.com.cpcjrdev.core.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.onFailure
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val webSocketConnector: KtorWebSocketConnector,
    private val applicationScope: CoroutineScope
) : MessageRepository {

    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            database.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
        return chatMessageService
            .fetchMessages(chatId, before)
            .onSuccess { messages ->
                return safeDatabaseUpdate {
                    database.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize = ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before == null // Only sync for most recent page
                    )
                    messages
                }
            }
    }

    override suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError> {
        return safeDatabaseUpdate {
            val dto = message.toWebSocketDto()

            val localUser = sessionStorage.observeAuthInfo().first()?.user
                ?: return Result.Failure(DataError.Local.NOT_FOUND)

            val entity = dto.toEntity(
                senderId = localUser.id,
                deliveryStatus = ChatMessageDeliveryStatus.SENDING
            )
            database.chatMessageDao.upsertMessage(entity)

            return webSocketConnector
                .sendMessage(dto.toJsonPayload())
                .onFailure { error ->
                    applicationScope.launch {
                        database.chatMessageDao.upsertMessage(
                            dto.toEntity(
                                senderId = localUser.id,
                                deliveryStatus = ChatMessageDeliveryStatus.FAILED
                            )
                        )
                    }.join()
                }
        }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return database
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { messages ->
                messages.map { it.toDomain() }
            }
    }

    private fun OutgoingWebSocketDto.NewMessage.toJsonPayload(): String {
        val webSocketMessage = WebSocketMessageDto(
            type = type.name,
            payload = json.encodeToString(this)
        )
        return json.encodeToString(webSocketMessage)
    }
}