package br.com.cpcjrdev.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.cpcjrdev.auth.presentantion.navigation.AuthGraphRoutes
import br.com.cpcjrdev.auth.presentantion.navigation.authGraph
import br.com.cpcjrdev.chat.presentantion.navigation.ChatGraphRoutes
import br.com.cpcjrdev.chat.presentantion.navigation.chatGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate(route = ChatGraphRoutes.Graph) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            },
        )
        chatGraph(navController = navController)
    }
}
