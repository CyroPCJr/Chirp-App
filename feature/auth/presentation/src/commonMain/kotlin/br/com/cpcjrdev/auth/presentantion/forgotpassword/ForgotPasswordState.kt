package br.com.cpcjrdev.auth.presentantion.forgotpassword

import androidx.compose.foundation.text.input.TextFieldState
import br.com.cpcjrdev.core.presentantion.util.UiText

data class ForgotPasswordState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val emailError: UiText? = null,
    val canSubmit: Boolean = false,
    val isLoading: Boolean = false,
    val errorText: UiText? = null,
    val isEmailSentSuccessfully: Boolean = false,
)
