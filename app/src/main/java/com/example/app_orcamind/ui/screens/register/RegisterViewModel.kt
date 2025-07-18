package com.example.app_orcamind.ui.screens.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    var userResponseRegisterEmail by mutableStateOf("")
        private set

    var userResponseRegisterPassword by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var createUserErrorMessage by mutableStateOf<String?>(null)

    var createUserSuccess by mutableStateOf(false)
        private set

    // Instância do Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun createUserEmail(newEmail: String) {
        userResponseRegisterEmail = newEmail
        // Limpa mensagens de erro ao digitar novamente
        createUserErrorMessage = null
    }

    fun createUserPassword(newPassword: String) {
        userResponseRegisterPassword = newPassword
        createUserErrorMessage = null
    }

    fun performCreateUser() {
        isLoading = true
        createUserErrorMessage = null
        createUserSuccess = false


        if (userResponseRegisterEmail.isBlank() || userResponseRegisterPassword.isBlank()) {
            createUserErrorMessage = "Por favor, preencha todos os campos"
            isLoading = false
            return
        }

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(
                    userResponseRegisterEmail,
                    userResponseRegisterPassword
                )
                    .await()
                createUserSuccess = true

                Log.i("createFirebase", "Create User Success")

            } catch (e: FirebaseAuthException) {
                createUserSuccess = false
                createUserErrorMessage = when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "O e-mail digitado é inválido."
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "Este e-mail já está em uso."
                    "ERROR_WEAK_PASSWORD" -> "Senha muito fraca."
                    // Adicione mais casos conforme necessário para mensagens mais amigáveis
                    else -> "Erro de cadastro: ${e.message}"
                }
            } catch (e: Exception) {
                // Erro genérico
                createUserSuccess = false
                createUserErrorMessage = "Erro desconhecido ao se registrar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}