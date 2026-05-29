package br.com.cpcjrdev.chat.domain.chat

import br.com.cpcjrdev.chat.domain.error.ConnectionError
import br.com.cpcjrdev.chat.domain.models.ChatMessage
import br.com.cpcjrdev.chat.domain.models.ConnectionState
import br.com.cpcjrdev.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<ChatMessage>
    val connectionState: StateFlow<ConnectionState>
    suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError>
}