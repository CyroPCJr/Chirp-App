package br.com.cpcjrdev.auth.presentantion.resetpassword

sealed interface ResetPasswordAction {
    data object OnSubmitClick : ResetPasswordAction

    data object OnTogglePasswordVisibilityClick : ResetPasswordAction
}
