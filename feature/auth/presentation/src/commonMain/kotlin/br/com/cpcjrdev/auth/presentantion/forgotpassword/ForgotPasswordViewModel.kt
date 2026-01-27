package br.com.cpcjrdev.auth.presentantion.forgotpassword

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cpcjrdev.auth.domain.EmailValidator
import br.com.cpcjrdev.core.domain.auth.AuthService
import br.com.cpcjrdev.core.domain.util.onFailure
import br.com.cpcjrdev.core.domain.util.onSuccess
import br.com.cpcjrdev.core.presentantion.util.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authService: AuthService,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val isEmailValidFlow =
        snapshotFlow {
            state.value.emailTextFieldState.text
                .toString()
        }.map { email -> EmailValidator.validate(email) }
            .distinctUntilChanged()

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state =
        _state
            .onStart {
                if (!hasLoadedInitialData) {
                    observeValidationState()
                    hasLoadedInitialData = true
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ForgotPasswordState(),
            )

    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            is ForgotPasswordAction.OnSubmitClick -> submitForgotPasswordRequest()
        }
    }

    private fun submitForgotPasswordRequest() {
        if (state.value.isLoading || !state.value.canSubmit) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isEmailSentSuccessfully = false,
                    errorText = null,
                )
            }

            val email =
                state.value.emailTextFieldState.text
                    .toString()
            authService
                .forgotPassword(email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isEmailSentSuccessfully = true,
                            isLoading = false,
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            errorText = error.toUiText(),
                            isLoading = false,
                        )
                    }
                }
        }
    }

    private fun observeValidationState() {
        isEmailValidFlow
            .onEach { isEmailValid ->
                _state.update {
                    it.copy(
                        canSubmit = isEmailValid,
                    )
                }
            }.launchIn(viewModelScope)
    }
}
