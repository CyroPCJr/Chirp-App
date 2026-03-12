package br.com.cpcjrdev.chat.domain.chat

import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>

    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}
