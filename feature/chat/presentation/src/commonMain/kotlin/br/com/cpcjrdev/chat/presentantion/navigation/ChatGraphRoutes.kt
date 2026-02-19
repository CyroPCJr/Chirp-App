package br.com.cpcjrdev.chat.presentantion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.cpcjrdev.chat.presentantion.chatlistdetail.ChatListDetailAdaptiveLayout
import kotlinx.serialization.Serializable

sealed interface ChatGraphRoutes {
    @Serializable
    data object Graph : ChatGraphRoutes

    @Serializable
    data object ChatListDetailRoute : ChatGraphRoutes
}

fun NavGraphBuilder.chatGraph(navController: NavController) {
    navigation<ChatGraphRoutes.Graph>(
        startDestination = ChatGraphRoutes.ChatListDetailRoute,
    ) {
        composable<ChatGraphRoutes.ChatListDetailRoute> {
            ChatListDetailAdaptiveLayout()
        }
    }
}
