package com.example.app_orcamind.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val uid = firebaseAuth.currentUser?.uid ?: return@launch
            try {
                val documentSnapshot = firestore.collection("users")
                    .document(uid)
                    .collection("home")
                    .document("dados")
                    .get()
                    .await()  // suspende até obter o resultado

                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.toObject(HomeUiState::class.java)
                    data?.let {
                        _uiState.value = it
                    }
                }
            } catch (e: FirebaseFirestoreException) {
                // tratar erro, por exemplo:
                Log.e("HomeScreenViewModel", "Erro ao carregar dados", e)
            }
        }
    }

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
        val revenue = state.revenue.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val expense = state.expense.toBigDecimalOrNull() ?: BigDecimal.ZERO
        return (revenue - expense).toPlainString()
    }

    fun saveHomeStateFirestore() {
        val user = firebaseAuth.currentUser
        val uid = user?.uid ?: return

        val homeData = _uiState.value

        firestore.collection("users")
            .document(uid)
            .collection("home")
            .document("dados")
            .set(homeData)
            .addOnSuccessListener {
                Log.i("Firestore", "Dados salvos com sucesso!")
            }
            .addOnFailureListener { e ->
                Log.i("Firestore", "Erro ao salvar dados: ${e.message}", e)
            }
    }

}