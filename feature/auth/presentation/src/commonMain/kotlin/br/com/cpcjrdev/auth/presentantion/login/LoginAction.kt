package br.com.cpcjrdev.auth.presentantion.login

sealed interface LoginAction {
    data object OnTogglePasswordVisibility : LoginAction

    data object OnForgotPasswordClick : LoginAction

    data object OnLoginClick : LoginAction

    data object OnSignUpClick : LoginAction
}
