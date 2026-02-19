package br.com.cpcjrdev.chat.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val otherUserIds: List<String>,
)
