package br.com.cpcjrdev.auth.presentantion.emailverification

data class EmailVerificationState(
    val isVerifying: Boolean = false,
    val isVerified: Boolean = false,
)
