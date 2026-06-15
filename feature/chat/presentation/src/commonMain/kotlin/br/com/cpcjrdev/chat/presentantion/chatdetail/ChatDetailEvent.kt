package br.com.cpcjrdev.chat.presentantion.chatdetail

import br.com.cpcjrdev.core.presentantion.util.UiText

sealed interface ChatDetailEvent {
    data object OnChatLeft : ChatDetailEvent

    data class OnError(
        val error: UiText,
    ) : ChatDetailEvent

    data object OnNewMessage: ChatDetailEvent
}
