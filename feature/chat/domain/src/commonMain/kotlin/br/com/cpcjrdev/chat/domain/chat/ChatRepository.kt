package br.com.cpcjrdev.chat.domain.chat

import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.chat.domain.models.ChatInfo
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>

    fun getChatInfoById(chatId: String): Flow<ChatInfo>

    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>

    suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote>

    suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote>

    suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote>
}
