package br.com.cpcjrdev.chat.data.chat

import br.com.cpcjrdev.chat.data.mappers.toDomain
import br.com.cpcjrdev.chat.data.mappers.toEntity
import br.com.cpcjrdev.chat.data.mappers.toLastMessageView
import br.com.cpcjrdev.chat.database.ChirpChatDatabase
import br.com.cpcjrdev.chat.database.entities.ChatInfoEntity
import br.com.cpcjrdev.chat.database.entities.ChatParticipantEntity
import br.com.cpcjrdev.chat.database.entities.ChatWithParticipants
import br.com.cpcjrdev.chat.domain.chat.ChatRepository
import br.com.cpcjrdev.chat.domain.chat.ChatService
import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.chat.domain.models.ChatInfo
import br.com.cpcjrdev.chat.domain.models.ChatParticipant
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result
import br.com.cpcjrdev.core.domain.util.asEmptyResult
import br.com.cpcjrdev.core.domain.util.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase,
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> =
        db.chatDao
            .getChatsWithParticipants()
            .map { allChatsWithParticipants ->
                supervisorScope {
                    allChatsWithParticipants
                        .map { chatWithParticipants ->
                            async {
                                ChatWithParticipants(
                                    chat = chatWithParticipants.chat,
                                    participants =
                                        chatWithParticipants
                                            .participants
                                            .onlyActive(chatWithParticipants.chat.chatId),
                                    lastMessage = chatWithParticipants.lastMessage,
                                )
                            }
                        }.awaitAll()
                        .map { it.toDomain() }
                }
            }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> =
        chatService
            .getChats()
            .onSuccess { chats ->
                val chatsWithParticipants =
                    chats.map { chat ->
                        ChatWithParticipants(
                            chat = chat.toEntity(),
                            participants = chat.participants.map { it.toEntity() },
                            lastMessage = chat.lastMessage?.toLastMessageView(),
                        )
                    }

                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatsWithParticipants,
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                    messageDao = db.chatMessageDao,
                )
            }

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> =
        db.chatDao
            .getChatInfoById(chatId)
            .filterNotNull()
            .map { chatInfo ->
                ChatInfoEntity(
                    chat = chatInfo.chat,
                    participants =
                        chatInfo
                            .participants
                            .onlyActive(chatInfo.chat.chatId),
                    messagesWithSenders = chatInfo.messagesWithSenders,
                )
            }.map { it.toDomain() }

    override fun getActiveParticipantsByChatId(chatId: String): Flow<List<ChatParticipant>> =
        db.chatDao
            .getActiveParticipantsByChatId(chatId)
            .map { participants ->
                participants.map { it.toDomain() }
            }

    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> =
        chatService
            .getChatById(chatId)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participants.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                )
            }.asEmptyResult()

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> =
        chatService
            .createChat(otherUserIds)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participants.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                )
            }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> =
        chatService.leaveChat(chatId).onSuccess { db.chatDao.deleteChatById(chatId) }

    override suspend fun addParticipantsToChat(
        chatId: String,
        userIds: List<String>,
    ): Result<Chat, DataError.Remote> =
        chatService
            .addParticipantsToChat(chatId, userIds)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participants.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                )
            }

    private suspend fun List<ChatParticipantEntity>.onlyActive(chatId: String): List<ChatParticipantEntity> {
        val activeParticipantIds =
            db
                .chatDao
                .getActiveParticipantsByChatId(chatId)
                .first()
                .map { it.userId }

        return this.filter { it.userId in activeParticipantIds }
    }
}
