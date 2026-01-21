package br.com.cpcjrdev.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.cpcjrdev.auth.presentantion.navigation.AuthGraphRoutes
import br.com.cpcjrdev.auth.presentantion.navigation.authGraph
import br.com.cpcjrdev.chat.presentantion.chatlist.ChatListRoute
import br.com.cpcjrdev.chat.presentantion.chatlist.ChatListScreenRoot

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph,
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate(route = ChatListRoute) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            },
        )
        composable<ChatListRoute> {
            ChatListScreenRoot()
        }
    }
}
