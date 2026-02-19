package br.com.cpcjrdev.chat.data.chat

import br.com.cpcjrdev.chat.data.dto.ChatParticipantDto
import br.com.cpcjrdev.chat.data.mappers.toDomain
import br.com.cpcjrdev.chat.domain.chat.ChatParticipantService
import br.com.cpcjrdev.chat.domain.models.ChatParticipant
import br.com.cpcjrdev.core.data.networking.get
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.Result
import br.com.cpcjrdev.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatParticipantService(
    private val httpClient: HttpClient,
) : ChatParticipantService {
    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> =
        httpClient
            .get<ChatParticipantDto>(
                route = "/participants",
                queryParams =
                    mapOf(
                        "query" to query,
                    ),
            ).map { it.toDomain() }
}
