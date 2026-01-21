package br.com.cpcjrdev.auth.presentantion.login

sealed interface LoginEvent {
    data object Success : LoginEvent
}
