package br.com.cpcjrdev.chat.domain.chat

import br.com.cpcjrdev.chat.domain.models.ChatParticipant
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote>
}
