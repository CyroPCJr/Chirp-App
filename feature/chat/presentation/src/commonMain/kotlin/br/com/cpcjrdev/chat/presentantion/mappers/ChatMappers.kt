package br.com.cpcjrdev.chat.presentantion.mappers

import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.chat.presentantion.model.ChatUi

fun Chat.toUi(localParticipantId: String): ChatUi {
    val (local, other) = participants.partition { it.userId == localParticipantId }
    return ChatUi(
        id = id,
        localParticipant = local.first().toUi(),
        otherParticipants = other.map { it.toUi() },
        lastMessage = lastMessage,
        lastMessageSenderUsername =
            participants
                .find { it.userId == lastMessage?.senderId }
                ?.username,
    )
}
