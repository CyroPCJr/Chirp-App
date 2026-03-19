package br.com.cpcjrdev.chat.data.mappers

import br.com.cpcjrdev.chat.data.dto.ChatParticipantDto
import br.com.cpcjrdev.chat.database.entities.ChatParticipantEntity
import br.com.cpcjrdev.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toDomain(): ChatParticipant =
    ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
    )

fun ChatParticipantEntity.toDomain(): ChatParticipant =
    ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
    )

fun ChatParticipant.toEntity(): ChatParticipantEntity =
    ChatParticipantEntity(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
    )
