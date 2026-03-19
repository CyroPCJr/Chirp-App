package br.com.cpcjrdev.chat.presentantion.managechat

sealed interface ManageChatEvent {
    data object OnMembersAdded : ManageChatEvent
}
