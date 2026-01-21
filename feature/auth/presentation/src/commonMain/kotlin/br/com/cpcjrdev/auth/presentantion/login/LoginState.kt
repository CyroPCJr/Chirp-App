package br.com.cpcjrdev.auth.presentantion.login

import androidx.compose.foundation.text.input.TextFieldState
import br.com.cpcjrdev.core.presentantion.util.UiText

data class LoginState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val error: UiText? = null,
)
