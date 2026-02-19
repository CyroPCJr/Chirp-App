package br.com.cpcjrdev.chat.data.chat

import br.com.cpcjrdev.chat.data.dto.ChatDto
import br.com.cpcjrdev.chat.data.dto.request.CreateChatRequest
import br.com.cpcjrdev.chat.data.mappers.toDomain
import br.com.cpcjrdev.chat.domain.chat.ChatService
import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.core.data.networking.post
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.Result
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
}
