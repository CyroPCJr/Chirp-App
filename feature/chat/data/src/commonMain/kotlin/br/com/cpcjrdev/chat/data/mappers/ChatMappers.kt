package br.com.cpcjrdev.chat.data.mappers

import br.com.cpcjrdev.chat.data.dto.ChatDto
import br.com.cpcjrdev.chat.database.entities.ChatEntity
import br.com.cpcjrdev.chat.database.entities.ChatWithParticipants
import br.com.cpcjrdev.chat.domain.models.Chat
import kotlin.time.Instant

fun ChatDto.toDomain(): Chat =
    Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain(),
    )

fun ChatWithParticipants.toDomain(): Chat =
    Chat(
        id = chat.chatId,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toDomain(),
    )

fun Chat.toEntity(): ChatEntity =
    ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds(),
    )
