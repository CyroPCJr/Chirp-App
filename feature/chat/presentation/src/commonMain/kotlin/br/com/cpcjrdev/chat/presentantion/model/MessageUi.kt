package br.com.cpcjrdev.chat.presentantion.model

import br.com.cpcjrdev.chat.domain.models.ChatMessageDeliveryStatus
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi
import br.com.cpcjrdev.core.presentantion.util.UiText

sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val isMenuOpen: Boolean,
        val formattedSentTime: UiText,
    ) : MessageUi

    data class OtherUserMessage(
        val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi,
    ) : MessageUi

    data class DateSeparator(
        val id: String,
        val date: UiText,
    ) : MessageUi
}
