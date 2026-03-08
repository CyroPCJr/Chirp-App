package br.com.cpcjrdev.chat.presentantion.chatdetail

import androidx.compose.foundation.text.input.TextFieldState
import br.com.cpcjrdev.chat.domain.models.ConnectionState
import br.com.cpcjrdev.chat.presentantion.model.ChatUi
import br.com.cpcjrdev.chat.presentantion.model.MessageUi
import br.com.cpcjrdev.core.presentantion.util.UiText

data class ChatDetailState(
    val chatUi: ChatUi? = null,
    val isLoading: Boolean = false,
    val messages: List<MessageUi> = emptyList(),
    val error: UiText? = null,
    val messageTextFieldState: TextFieldState = TextFieldState(),
    val canSendMessage: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val paginationError: UiText? = null,
    val endReached: Boolean = false,
    val bannerState: BannerState = BannerState(),
    val isChatOptionsOpen: Boolean = false,
    val isNearBottom: Boolean = false,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
)

data class BannerState(
    val formattedDate: UiText? = null,
    val isVisible: Boolean = false,
)
