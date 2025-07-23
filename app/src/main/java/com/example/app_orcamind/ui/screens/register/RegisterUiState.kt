package com.example.app_orcamind.ui.screens.register

data class RegisterUiState(
    val userResponseRegisterEmail: String = "",
    val userResponseRegisterPassword: String = "",
    val isLoading: Boolean = false,
    val createUserErrorMessage: String? = null,
    val createUserSuccess: Boolean = false
)
