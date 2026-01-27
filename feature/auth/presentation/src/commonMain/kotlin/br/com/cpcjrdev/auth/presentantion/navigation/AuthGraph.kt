package br.com.cpcjrdev.auth.presentantion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import br.com.cpcjrdev.auth.presentantion.emailverification.EmailVerificationRoot
import br.com.cpcjrdev.auth.presentantion.forgotpassword.ForgotPasswordRoot
import br.com.cpcjrdev.auth.presentantion.login.LoginRoot
import br.com.cpcjrdev.auth.presentantion.register.RegisterRoot
import br.com.cpcjrdev.auth.presentantion.registersuccess.RegisterSuccessRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Register,
    ) {
        composable<AuthGraphRoutes.Login> {
            LoginRoot(
                onLoginSuccess = onLoginSuccess,
                onForgotPasswordClick = {
                    navController.navigate(AuthGraphRoutes.ForgotPassword)
                },
                onCreateAccountClick = {
                    navController.navigate(AuthGraphRoutes.Register) {
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(it))
                },
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.RegisterSuccess> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks =
                listOf(
                    navDeepLink {
                        this.uriPattern =
                            "https://chirp.pl-coding.com/api/auth/verify?token={token}"
                    },
                    navDeepLink {
                        this.uriPattern =
                            "chirp://chirp.pl-coding.com/api/auth/verify?token={token}"
                    },
                ),
        ) {
            EmailVerificationRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.EmailVerification> {
                            inclusive = true
                        }
                    }
                },
                onCloseClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.EmailVerification> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<AuthGraphRoutes.ForgotPassword> {
            ForgotPasswordRoot()
        }
    }
}
