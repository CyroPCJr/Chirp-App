package br.com.cpcjrdev.chat.presentantion.createchat

sealed interface CreateChatAction {
    data object OnAddClick : CreateChatAction

    data object OnDismissDialog : CreateChatAction

    data object OnCreateChatClick : CreateChatAction
}
