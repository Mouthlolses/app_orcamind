package com.example.app_orcamind.ui.screens.login

data class LoginUiState(
    val userResponseEmail: String = "",
    val userResponsePassword: String = "",
    val isLoading: Boolean = false,
    val loginErrorMessage: String? = null,
    val loginSuccess: Boolean = false
)
