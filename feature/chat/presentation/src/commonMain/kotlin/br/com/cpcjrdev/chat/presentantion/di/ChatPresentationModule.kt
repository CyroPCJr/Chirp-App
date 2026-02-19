package br.com.cpcjrdev.chat.presentantion.di

import br.com.cpcjrdev.chat.presentantion.chatlist.ChatListViewModel
import br.com.cpcjrdev.chat.presentantion.chatlistdetail.ChatListDetailViewModel
import br.com.cpcjrdev.chat.presentantion.createchat.CreateChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationModule =
    module {
        viewModelOf(::ChatListViewModel)
        viewModelOf(::ChatListDetailViewModel)
        viewModelOf(::CreateChatViewModel)
    }
