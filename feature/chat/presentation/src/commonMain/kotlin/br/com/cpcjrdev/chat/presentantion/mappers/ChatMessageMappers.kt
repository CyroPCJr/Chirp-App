package br.com.cpcjrdev.chat.presentantion.mappers

import br.com.cpcjrdev.chat.domain.models.MessageWithSender
import br.com.cpcjrdev.chat.presentantion.model.MessageUi
import br.com.cpcjrdev.chat.presentantion.model.MessageUi.LocalUserMessage
import br.com.cpcjrdev.chat.presentantion.util.DateUtils

fun MessageWithSender.toUi(
    localUserId: String,
): MessageUi {
    val isFromLocalUser = this.sender.userId == localUserId
    return if (isFromLocalUser) {
        LocalUserMessage(
            id = message.id,
            content = message.content,
            deliveryStatus = message.deliveryStatus,
            isMenuOpen = false,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createdAt)
        )
    } else {
        MessageUi.OtherUserMessage(
            id = message.id,
            content = message.content,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createdAt),
            sender = sender.toUi()
        )
    }
}