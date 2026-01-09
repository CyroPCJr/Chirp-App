package br.com.cpcjrdev.chirp

import androidx.compose.runtime.Composable
import br.com.cpcjrdev.auth.presentantion.register.RegisterRoot
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterRoot()
    }
}
