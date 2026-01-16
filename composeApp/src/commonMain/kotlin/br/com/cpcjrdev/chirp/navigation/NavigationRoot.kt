package br.com.cpcjrdev.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.cpcjrdev.auth.presentantion.navigation.AuthGraphRoutes
import br.com.cpcjrdev.auth.presentantion.navigation.authGraph

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph,
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
            },
        )
    }
}
