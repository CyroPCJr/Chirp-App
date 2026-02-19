package br.com.cpcjrdev.chat.presentantion.mappers

import br.com.cpcjrdev.chat.domain.models.ChatParticipant
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toUi(): ChatParticipantUi =
    ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl,
    )
