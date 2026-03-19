package br.com.cpcjrdev.chat.data.chat

import br.com.cpcjrdev.chat.data.dto.ChatDto
import br.com.cpcjrdev.chat.data.dto.request.CreateChatRequest
import br.com.cpcjrdev.chat.data.dto.request.ParticipantsRequest
import br.com.cpcjrdev.chat.data.mappers.toDomain
import br.com.cpcjrdev.chat.domain.chat.ChatService
import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.core.data.networking.delete
import br.com.cpcjrdev.core.data.networking.get
import br.com.cpcjrdev.core.data.networking.post
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result
import br.com.cpcjrdev.core.domain.util.asEmptyResult
import br.com.cpcjrdev.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatService(
    private val httpClient: HttpClient,
) : ChatService {
    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> =
        httpClient
            .post<CreateChatRequest, ChatDto>(
                route = "/chat",
                body =
                    CreateChatRequest(
                        otherUserIds = otherUserIds,
                    ),
            ).map { it.toDomain() }

    override suspend fun getChats(): Result<List<Chat>, DataError.Remote> =
        httpClient
            .get<List<ChatDto>>(
                route = "/chat",
            ).map { chatDtos ->
                chatDtos.map { it.toDomain() }
            }

    override suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote> =
        httpClient
            .get<ChatDto>(
                route = "/chat/$chatId",
            ).map { it.toDomain() }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> =
        httpClient
            .delete<Unit>(
                route = "/chat/$chatId/leave",
            ).asEmptyResult()

    override suspend fun addParticipantsToChat(
        chatId: String,
        userIds: List<String>,
    ): Result<Chat, DataError.Remote> =
        httpClient
            .post<ParticipantsRequest, ChatDto>(
                route = "/chat/$chatId/add",
                body =
                    ParticipantsRequest(
                        userIds = userIds,
                    ),
            ).map { it.toDomain() }
}
