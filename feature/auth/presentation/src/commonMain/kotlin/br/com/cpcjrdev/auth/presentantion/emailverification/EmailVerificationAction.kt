package br.com.cpcjrdev.auth.presentantion.emailverification

sealed interface EmailVerificationAction {
    data object OnLoginClick : EmailVerificationAction

    data object OnCloseClick : EmailVerificationAction
}
