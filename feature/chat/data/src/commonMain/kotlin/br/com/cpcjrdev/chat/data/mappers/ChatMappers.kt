package br.com.cpcjrdev.chat.data.mappers

import br.com.cpcjrdev.chat.data.dto.ChatDto
import br.com.cpcjrdev.chat.domain.models.Chat
import kotlin.time.Instant

fun ChatDto.toDomain(): Chat =
    Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain(),
    )
