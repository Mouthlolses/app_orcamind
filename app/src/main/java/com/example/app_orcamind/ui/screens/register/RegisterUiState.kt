package com.example.app_orcamind.ui.screens.register

data class RegisterUiState(
    val userResponseRegisterName: String = "",
    val userResponseRegisterEmail: String = "",
    val userResponseRegisterPassword: String = "",
    val userResponseRegisterPasswordCurrent: String = "",
    val isLoading: Boolean = false,
    val createUserErrorMessage: String? = null,
    val createUserSuccess: Boolean = false
)
