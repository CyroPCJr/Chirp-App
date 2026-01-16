package br.com.cpcjrdev.auth.presentantion.registersuccess

sealed interface RegisterSuccessEvent {
    data object ResendVerificationEmailSuccess : RegisterSuccessEvent
}
