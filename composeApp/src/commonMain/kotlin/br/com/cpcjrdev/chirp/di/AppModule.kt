package br.com.cpcjrdev.chirp.di

import br.com.cpcjrdev.chirp.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModelOf(::MainViewModel)
    }
