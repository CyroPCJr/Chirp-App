package br.com.cpcjrdev.chat.presentantion.chatlistdetail

sealed interface ChatListDetailAction {
    data class OnSelectChat(
        val chatId: String?,
    ) : ChatListDetailAction

    data object OnProfileSettingsClick : ChatListDetailAction

    data object OnCreateChatClick : ChatListDetailAction

    data object OnManageChatClick : ChatListDetailAction

    data object OnDismissCurrentDialog : ChatListDetailAction
}
