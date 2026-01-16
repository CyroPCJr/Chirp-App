package br.com.cpcjrdev.chirp

import androidx.compose.runtime.Composable
import br.com.cpcjrdev.chirp.navigation.NavigationRoot
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        NavigationRoot()
    }
}
