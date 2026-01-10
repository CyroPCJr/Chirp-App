package br.com.cpcjrdev.auth.presentantion.register

sealed interface RegisterEvent {
    data class Success(
        val email: String,
    ) : RegisterEvent
}
