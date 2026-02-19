package br.com.cpcjrdev.chat.data.mappers

import br.com.cpcjrdev.chat.data.dto.ChatMessageDto
import br.com.cpcjrdev.chat.domain.models.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage =
    ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId,
    )
