package br.com.cpcjrdev.chat.presentantion.chatlist

import br.com.cpcjrdev.chat.presentantion.model.ChatUi
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi
import br.com.cpcjrdev.core.presentantion.util.UiText

data class ChatListState(
    val chats: List<ChatUi> = emptyList(),
    val error: UiText? = null,
    val localParticipant: ChatParticipantUi? = null,
    val isUserMenuOpen: Boolean = false,
    val showLogoutConfirmation: Boolean = false,
    val selectedChatId: String? = null,
    val isLoading: Boolean = false,
)
