package com.example.app_orcamind.ui.screens.home

data class HomeUiState(
    val userName: String = "",
    val revenue: String = "",
    val expense: String = "",
    val economy: String = "",
    val balance: String = "",
    val revenueValue: Long = 0L,
    val expenseValue: Long = 0L,
    val balanceValue: Long = 0L
)
