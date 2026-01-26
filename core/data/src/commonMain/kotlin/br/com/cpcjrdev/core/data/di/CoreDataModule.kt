package br.com.cpcjrdev.core.data.di

import br.com.cpcjrdev.core.data.auth.DataStoreSessionStorage
import br.com.cpcjrdev.core.data.auth.KtorAuthService
import br.com.cpcjrdev.core.data.logging.KermitLogger
import br.com.cpcjrdev.core.data.networking.HttpClientFactory
import br.com.cpcjrdev.core.domain.auth.AuthService
import br.com.cpcjrdev.core.domain.auth.SessionStorage
import br.com.cpcjrdev.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule =
    module {
        includes(platformCoreDataModule)
        single<ChirpLogger> { KermitLogger }
        single {
            HttpClientFactory(get(), get()).create(get())
        }
        singleOf(::KtorAuthService) bind AuthService::class
        singleOf(::DataStoreSessionStorage) bind SessionStorage::class
    }
