package br.com.cpcjrdev.chirp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import br.com.cpcjrdev.auth.presentantion.navigation.AuthGraphRoutes
import br.com.cpcjrdev.chat.presentantion.chatlist.ChatListRoute
import br.com.cpcjrdev.chat.presentantion.navigation.ChatGraphRoutes
import br.com.cpcjrdev.chirp.navigation.DeepLinkListener
import br.com.cpcjrdev.chirp.navigation.NavigationRoot
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import br.com.cpcjrdev.core.presentantion.util.ObserveAsEvents
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    onAuthenticationChecked: () -> Unit = {},
    viewModel: MainViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    DeepLinkListener(navController = navController)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingAuth) {
        if (!state.isCheckingAuth) {
            onAuthenticationChecked()
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            MainEvent.OnSessionExpired -> {
                navController.navigate(AuthGraphRoutes.Graph) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = false
                    }
                }
            }
        }
    }

    ChirpTheme {
        if (!state.isCheckingAuth) {
            NavigationRoot(
                navController = navController,
                startDestination =
                    if (state.isLoggedIn) {
                        ChatGraphRoutes.Graph
                    } else {
                        AuthGraphRoutes.Graph
                    },
            )
        }
    }
}
