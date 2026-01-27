package br.com.cpcjrdev.auth.presentantion.forgotpassword

sealed interface ForgotPasswordAction {
    data object OnSubmitClick : ForgotPasswordAction
}
