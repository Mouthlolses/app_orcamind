package com.example.app_orcamind.ui.screens.home

data class HomeUiState(
    val revenue: String = "",
    val expense: String = "",
    val balance: String = "",
    val revenueValue: Long = 0L,
    val expenseValue: Long = 0L,
    val balanceValue: Long = 0L
)
