package com.mohsin.dailytaskmanager

import AuthCheck
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.mohsin.dailytaskmanager.data.TaskRepository
import com.mohsin.dailytaskmanager.models.TaskViewModel
import com.mohsin.dailytaskmanager.models.TaskViewModelFactory
import com.mohsin.dailytaskmanager.ui.MainScreen

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val MAIN = "main"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    auth: FirebaseAuth
) {
    NavHost(navController = navController, startDestination = "auth_check") {
        composable("auth_check") {
            AuthCheck(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                auth = auth,
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Routes.SIGNUP)
                }
            )
        }

        composable(Routes.SIGNUP) {
            SignupScreen(
                auth = auth,
                onLoginClick = {
                    navController.navigate(Routes.LOGIN)
                },
                onSignupSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            val repository = TaskRepository(auth)
            val viewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(repository)
            )
            MainScreen(
                viewModel = viewModel,
                onLogout = {
                    auth.signOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
            )
        }
    }
}
