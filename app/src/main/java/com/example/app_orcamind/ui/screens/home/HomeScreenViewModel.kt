package com.example.app_orcamind.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    fun onRevenueChanged(newRevenue: String) {
        _uiState.update {
            val updated = it.copy(revenue = newRevenue)
            updated.copy(
                balance = calculateBalance(
                    state = updated
                )
            )
        }
    }

    fun onExpenseChanged(newExpense: String) {
        _uiState.update {
            val update = it.copy(expense = newExpense)
            update.copy(
                balance = calculateBalance(
                    state = update
                )
            )
        }
    }

    fun onEconomyChanged(newEconomy: String) {
        _uiState.update {
            it.copy(economy = newEconomy)
        }
    }

    private fun calculateBalance(state: HomeUiState): String {
        val revenue = state.revenue.toFloatOrNull() ?: 0f
        val expense = state.expense.toFloatOrNull() ?: 0f
        return (revenue - expense).toString()
    }

}