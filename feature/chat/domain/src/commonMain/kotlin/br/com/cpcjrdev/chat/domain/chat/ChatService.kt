package br.com.cpcjrdev.chat.domain.chat

import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.Result

interface ChatService {
    suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote>
}
