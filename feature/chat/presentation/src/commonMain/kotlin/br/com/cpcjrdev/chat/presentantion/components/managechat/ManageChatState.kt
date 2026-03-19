package br.com.cpcjrdev.chat.presentantion.components.managechat

import androidx.compose.foundation.text.input.TextFieldState
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi
import br.com.cpcjrdev.core.presentantion.util.UiText

data class ManageChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val existingChatParticipants: List<ChatParticipantUi> = emptyList(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isSearching: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null,
    val isSubmitting: Boolean = false,
    val submitError: UiText? = null,
)
