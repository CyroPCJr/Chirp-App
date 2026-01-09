package br.com.cpcjrdev.auth.presentantion.register

interface RegisterAction {
    data object OnLoginClick : RegisterAction

    data object OnInputTextFocusGain : RegisterAction

    data object OnRegisterClick : RegisterAction

    data object OnTogglePasswordVisibilityClick : RegisterAction
}
