package com.example.app_orcamind.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    fun onRevenueChanged(newRevenue: String) {
        _uiState.update { it.copy(revenue = newRevenue) }
    }

    fun onExpenseChanged(newExpense: String) {
        _uiState.update { it.copy(expense = newExpense) }
    }

    fun onBalanceChanged(newBalance: String){
        _uiState.update { it.copy(balance = newBalance) }
    }

}