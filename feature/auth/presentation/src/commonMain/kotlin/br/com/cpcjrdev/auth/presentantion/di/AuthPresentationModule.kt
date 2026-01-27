package br.com.cpcjrdev.auth.presentantion.di

import br.com.cpcjrdev.auth.presentantion.emailverification.EmailVerificationViewModel
import br.com.cpcjrdev.auth.presentantion.forgotpassword.ForgotPasswordViewModel
import br.com.cpcjrdev.auth.presentantion.register.RegisterViewModel
import br.com.cpcjrdev.auth.presentantion.registersuccess.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule =
    module {
        viewModelOf(::RegisterViewModel)
        viewModelOf(::RegisterSuccessViewModel)
        viewModelOf(::EmailVerificationViewModel)
        viewModelOf(::ForgotPasswordViewModel)
    }
