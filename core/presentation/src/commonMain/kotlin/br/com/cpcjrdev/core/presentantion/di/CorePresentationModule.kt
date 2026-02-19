package br.com.cpcjrdev.core.presentantion.di

import br.com.cpcjrdev.core.presentantion.util.ScopedStoreRegistryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val corePresentationModule =
    module {
        viewModelOf(::ScopedStoreRegistryViewModel)
    }
