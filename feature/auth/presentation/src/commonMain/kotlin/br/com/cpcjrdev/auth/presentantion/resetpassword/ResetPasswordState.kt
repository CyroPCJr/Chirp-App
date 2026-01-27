package br.com.cpcjrdev.auth.presentantion.resetpassword

import androidx.compose.foundation.text.input.TextFieldState
import br.com.cpcjrdev.core.presentantion.util.UiText

data class ResetPasswordState(
    val passwordTextState: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val errorText: UiText? = null,
    val isPasswordVisible: Boolean = false,
    val canSubmit: Boolean = false,
    val isResetSuccessful: Boolean = false,
)
