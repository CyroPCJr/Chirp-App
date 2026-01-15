package br.com.cpcjrdev.auth.presentantion.registersuccess

sealed interface RegisterSuccessAction {
    data object OnLoginClick : RegisterSuccessAction

    data object OnResendVerificationEmailClick : RegisterSuccessAction
}
