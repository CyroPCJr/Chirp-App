package br.com.cpcjrdev.chirp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import br.com.cpcjrdev.chirp.navigation.DeepLinkListener
import br.com.cpcjrdev.chirp.navigation.NavigationRoot
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    DeepLinkListener(navController = navController)
    ChirpTheme {
        NavigationRoot(navController = navController)
    }
}
