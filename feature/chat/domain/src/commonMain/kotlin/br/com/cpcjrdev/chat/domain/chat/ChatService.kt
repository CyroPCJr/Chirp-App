package br.com.cpcjrdev.chat.domain.chat

import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result

interface ChatService {
    suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote>

    suspend fun getChats(): Result<List<Chat>, DataError.Remote>

    suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote>

    suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote>

    suspend fun addParticipantsToChat(
        chatId: String,
        userIds: List<String>,
    ): Result<Chat, DataError.Remote>
}
