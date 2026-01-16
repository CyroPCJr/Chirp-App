package br.com.cpcjrdev.auth.presentantion.registersuccess

import br.com.cpcjrdev.core.presentantion.util.UiText

data class RegisterSuccessState(
    val registeredEmail: String = "",
    val isResendingVerificationEmail: Boolean = false,
    val resendVerificationError: UiText? = null,
)
