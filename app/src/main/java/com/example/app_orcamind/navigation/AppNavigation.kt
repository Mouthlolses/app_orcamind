package com.example.app_orcamind.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_orcamind.ui.screens.login.LoginScreen
import com.example.app_orcamind.ui.screens.register.RegisterScreen


@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "loginScreen"
    ) {
        composable("loginScreen"){
            LoginScreen(
                loginViewModel = viewModel(),
                navController = navController
            )
        }
        composable("registerScreen"){
            RegisterScreen()
        }
    }
}