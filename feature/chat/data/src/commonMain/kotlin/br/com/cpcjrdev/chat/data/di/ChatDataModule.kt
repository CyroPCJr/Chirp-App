package br.com.cpcjrdev.chat.data.di

import br.com.cpcjrdev.chat.data.chat.KtorChatParticipantService
import br.com.cpcjrdev.chat.data.chat.KtorChatService
import br.com.cpcjrdev.chat.domain.chat.ChatParticipantService
import br.com.cpcjrdev.chat.domain.chat.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule =
    module {
        singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
        singleOf(::KtorChatService) bind ChatService::class
    }
