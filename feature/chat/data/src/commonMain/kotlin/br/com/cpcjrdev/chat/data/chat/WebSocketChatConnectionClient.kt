package br.com.cpcjrdev.chat.data.chat

import br.com.cpcjrdev.chat.data.dto.websocket.WebSocketMessageDto
import br.com.cpcjrdev.chat.data.mappers.toNewMessage
import br.com.cpcjrdev.chat.data.network.KtorWebSocketConnector
import br.com.cpcjrdev.chat.database.ChirpChatDatabase
import br.com.cpcjrdev.chat.domain.chat.ChatConnectionClient
import br.com.cpcjrdev.chat.domain.chat.ChatRepository
import br.com.cpcjrdev.chat.domain.error.ConnectionError
import br.com.cpcjrdev.chat.domain.message.MessageRepository
import br.com.cpcjrdev.chat.domain.models.ChatMessage
import br.com.cpcjrdev.chat.domain.models.ChatMessageDeliveryStatus
import br.com.cpcjrdev.core.domain.auth.SessionStorage
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val webSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val database: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository
): ChatConnectionClient {

    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")

    override val connectionState = webSocketConnector.connectionState

    override suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError> {
        val outgoingDto = message.toNewMessage()
        val webSocketMessage = WebSocketMessageDto(
            type = outgoingDto.type.name,
            payload = json.encodeToString(outgoingDto)
        )
        val rawJsonPayload = json.encodeToString(webSocketMessage)

        return webSocketConnector
            .sendMessage(rawJsonPayload)
            .onFailure { error ->
                messageRepository.updateMessageDeliveryStatus(
                    messageId = message.id,
                    status = ChatMessageDeliveryStatus.FAILED
                )
            }
    }
}