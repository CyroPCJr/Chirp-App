package br.com.cpcjrdev.chat.data.mappers

import br.com.cpcjrdev.chat.data.dto.ChatDto
import br.com.cpcjrdev.chat.database.entities.ChatEntity
import br.com.cpcjrdev.chat.database.entities.ChatInfoEntity
import br.com.cpcjrdev.chat.database.entities.ChatWithParticipants
import br.com.cpcjrdev.chat.database.entities.MessageWithSender
import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.chat.domain.models.ChatInfo
import br.com.cpcjrdev.chat.domain.models.ChatMessage
import br.com.cpcjrdev.chat.domain.models.ChatMessageDeliveryStatus
import br.com.cpcjrdev.chat.domain.models.ChatParticipant
import kotlin.time.Instant

typealias DataMessageWithSender = MessageWithSender
typealias DomainMessageWithSender = br.com.cpcjrdev.chat.domain.models.MessageWithSender

fun ChatDto.toDomain(): Chat =
    Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain(),
    )

fun ChatEntity.toDomain(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage? = null,
): Chat =
    Chat(
        id = chatId,
        participants = participants,
        lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt),
        lastMessage = lastMessage,
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

fun DataMessageWithSender.toDomain(): DomainMessageWithSender =
    DomainMessageWithSender(
        message = message.toDomain(),
        sender = sender.toDomain(),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(this.message.deliveryStatus),
    )

fun ChatInfoEntity.toDomain(): ChatInfo =
    ChatInfo(
        chat =
            chat.toDomain(
                participants = this.participants.map { it.toDomain() },
            ),
        messages = messagesWithSenders.map { it.toDomain() },
    )
