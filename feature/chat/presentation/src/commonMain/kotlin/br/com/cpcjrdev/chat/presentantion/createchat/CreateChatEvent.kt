package br.com.cpcjrdev.chat.presentantion.createchat

import br.com.cpcjrdev.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(
        val chat: Chat,
    ) : CreateChatEvent
}
