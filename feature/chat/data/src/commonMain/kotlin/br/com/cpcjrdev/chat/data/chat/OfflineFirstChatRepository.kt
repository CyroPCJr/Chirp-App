package br.com.cpcjrdev.chat.data.chat

import br.com.cpcjrdev.chat.data.mappers.toDomain
import br.com.cpcjrdev.chat.data.mappers.toEntity
import br.com.cpcjrdev.chat.data.mappers.toLastMessageView
import br.com.cpcjrdev.chat.database.ChirpChatDatabase
import br.com.cpcjrdev.chat.database.entities.ChatWithParticipants
import br.com.cpcjrdev.chat.domain.chat.ChatRepository
import br.com.cpcjrdev.chat.domain.chat.ChatService
import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.Result
import br.com.cpcjrdev.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase,
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> =
        db.chatDao
            .getChatsWithActiveParticipants()
            .map { chatWithParticipantsList ->
                chatWithParticipantsList.map { it.toDomain() }
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
}
