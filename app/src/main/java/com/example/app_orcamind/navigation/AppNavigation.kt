package com.example.app_orcamind.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_orcamind.ui.screens.home.HomeScreen
import com.example.app_orcamind.ui.screens.login.LoginScreen
import com.example.app_orcamind.ui.screens.register.RegisterScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "loginScreen"
    ) {
        composable(
            "loginScreen",
        ) {
            LoginScreen(
                loginViewModel = viewModel(),
                navController = navController
            )
        }
        composable(
            "registerScreen"
        ) {
            RegisterScreen()
        }
        composable(
            "homeScreen"
        ) {
            HomeScreen()
        }
        composable(
            "homeSummyForm"
        ) {

        }
    }
}