package br.com.cpcjrdev.chat.presentantion.components.managechat

sealed interface ManageChatAction {
    data object OnAddClick : ManageChatAction

    data object OnDismissDialog : ManageChatAction

    data object OnPrimaryActionClick : ManageChatAction

    sealed interface ChatParticipants : ManageChatAction {
        data class OnSelectChat(
            val chatId: String?,
        ) : ManageChatAction
    }
}
