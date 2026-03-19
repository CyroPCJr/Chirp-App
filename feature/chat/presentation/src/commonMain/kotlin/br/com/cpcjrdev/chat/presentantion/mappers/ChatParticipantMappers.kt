package br.com.cpcjrdev.chat.presentantion.mappers

import br.com.cpcjrdev.chat.domain.models.ChatParticipant
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi
import br.com.cpcjrdev.core.domain.auth.User

fun ChatParticipant.toUi(): ChatParticipantUi =
    ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl,
    )

fun User.toUi(): ChatParticipantUi =
    ChatParticipantUi(
        id = id,
        username = username,
        initials = username.take(2).uppercase(),
        imageUrl = profilePictureUrl,
    )
