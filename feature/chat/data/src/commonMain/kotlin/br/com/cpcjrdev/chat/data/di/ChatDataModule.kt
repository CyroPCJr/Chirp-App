package br.com.cpcjrdev.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.com.cpcjrdev.chat.data.chat.KtorChatParticipantService
import br.com.cpcjrdev.chat.data.chat.KtorChatService
import br.com.cpcjrdev.chat.data.chat.OfflineFirstChatRepository
import br.com.cpcjrdev.chat.database.DatabaseFactory
import br.com.cpcjrdev.chat.domain.chat.ChatParticipantService
import br.com.cpcjrdev.chat.domain.chat.ChatRepository
import br.com.cpcjrdev.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module
val chatDataModule =
    module {
        includes(platformChatDataModule)
        singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
        singleOf(::KtorChatService) bind ChatService::class
        singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
        single {
            get<DatabaseFactory>()
                .create()
                .setDriver(BundledSQLiteDriver())
                .build()
        }
    }
