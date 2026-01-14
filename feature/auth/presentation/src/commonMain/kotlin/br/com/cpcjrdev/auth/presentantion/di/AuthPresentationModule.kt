package br.com.cpcjrdev.auth.presentantion.di

import br.com.cpcjrdev.auth.presentantion.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule =
    module {
        viewModelOf(::RegisterViewModel)
    }
