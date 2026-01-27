package br.com.cpcjrdev.chirp.di

import br.com.cpcjrdev.auth.presentantion.di.authPresentationModule
import br.com.cpcjrdev.chat.presentantion.di.chatPresentationModule
import br.com.cpcjrdev.core.data.di.coreDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule,
            chatPresentationModule,
        )
    }
}
