package br.com.cpcjrdev.auth.presentantion.registersuccess

data class RegisterSuccessState(
    val registeredEmail: String = "",
    val isResendingVerificationEmail: Boolean = false,
)
