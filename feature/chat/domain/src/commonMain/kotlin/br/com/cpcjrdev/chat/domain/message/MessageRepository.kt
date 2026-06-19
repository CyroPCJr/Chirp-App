package br.com.cpcjrdev.chat.domain.message

import br.com.cpcjrdev.chat.domain.models.ChatMessage
import br.com.cpcjrdev.chat.domain.models.ChatMessageDeliveryStatus
import br.com.cpcjrdev.chat.domain.models.MessageWithSender
import br.com.cpcjrdev.chat.domain.models.OutgoingNewMessage
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError>

    suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError>

    suspend fun retryMessage(messageId: String): EmptyResult<DataError>

    suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote>
    fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>>
}