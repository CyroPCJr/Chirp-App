package br.com.cpcjrdev.core.data.mappers

import br.com.cpcjrdev.core.data.dto.AuthInfoSerializable
import br.com.cpcjrdev.core.data.dto.UserSerializable
import br.com.cpcjrdev.core.domain.auth.AuthInfo
import br.com.cpcjrdev.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo =
    AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain(),
    )

fun UserSerializable.toDomain(): User =
    User(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl,
    )

fun User.toSerializable(): UserSerializable =
    UserSerializable(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl,
    )

fun AuthInfo.toSerializable(): AuthInfoSerializable =
    AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toSerializable(),
    )
