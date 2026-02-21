package br.com.cpcjrdev.chat.presentantion.model

import br.com.cpcjrdev.chat.domain.models.ChatMessage
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi

data class ChatUi(
    val id: String,
    val localParticipant: ChatParticipantUi,
    val otherParticipants: List<ChatParticipantUi>,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername: String?,
)
