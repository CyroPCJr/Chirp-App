package br.com.cpcjrdev.chat.data.di

import br.com.cpcjrdev.chat.data.lifecycle.AppLifecycleObserver
import br.com.cpcjrdev.chat.data.network.ConnectivityObserver
import br.com.cpcjrdev.chat.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory(androidContext()) }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
}