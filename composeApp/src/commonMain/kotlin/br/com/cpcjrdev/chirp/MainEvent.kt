package br.com.cpcjrdev.chirp

sealed interface MainEvent {
    data object OnSessionExpired : MainEvent
}
