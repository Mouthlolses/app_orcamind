package com.example.app_orcamind.ui.screens.login

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

class LoginViewModel : ViewModel() {

    var userResponseEmail by mutableStateOf("")
        private set

    var userResponsePassword by mutableStateOf("")
        private set

    // --- Variáveis de estado para o processo de login ---
    var isLoading by mutableStateOf(false)
        private set

    var loginErrorMessage by mutableStateOf<String?>(null)

    var loginSuccess by mutableStateOf(false)
        private set

    // Instância do Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // --- Funções para atualizar e-mail e senha ---
    fun updateUserEmail(newEmail: String) {
        userResponseEmail = newEmail
        // Limpa mensagens de erro ao digitar novamente
        loginErrorMessage = null
    }

    fun updateUserPassword(newPassword: String) {
        userResponsePassword = newPassword
        loginErrorMessage = null
    }

    fun performLogin() {
        isLoading = true // Indica que o login está em andamento
        loginErrorMessage = null // Limpa qualquer mensagem de erro anterior
        loginSuccess = false // Reseta o status de sucesso

        if (userResponseEmail.isBlank() || userResponsePassword.isBlank()) {
            loginErrorMessage = "Por favor, preencha todos os campos."
            isLoading = false
            return
        }

        viewModelScope.launch {
            try {
                // A chamada .await() é a mágica das corrotinas com Firebase KTX.
                // Ele converte a Task assíncrona do Firebase (signInWithEmailAndPassword) em uma função suspend.
                // Ela "espera" a conclusão da operação do Firebase de forma não-bloqueante.

                auth.signInWithEmailAndPassword(userResponseEmail,userResponsePassword).await()

                // Se chegou aqui, o login foi bem-sucedido
                loginSuccess = true
                Log.i("authFirebase","Login Success")

            } catch (e: Exception){
                loginSuccess = false
                loginErrorMessage = when (e) {
                    is FirebaseAuthException -> { // Erros específicos do Firebase
                        when (e.errorCode) {
                            "ERROR_INVALID_EMAIL" -> "O e-mail digitado é inválido."
                            "ERROR_WRONG_PASSWORD" -> "Senha incorreta."
                            "ERROR_USER_NOT_FOUND" -> "Usuário não encontrado."
                            "ERROR_USER_DISABLED" -> "Esta conta foi desativada."
                            // Adicione mais casos conforme necessário para mensagens mais amigáveis
                            else -> "Erro de autenticação: ${e.message}"
                        }
                    }
                    else -> "Erro desconhecido ao fazer login: ${e.message}"
                }
                println("Erro no login: ${e.message}")
            } finally {
              isLoading = false
            }
        }
    }

    fun resetLoginState() {
        isLoading = false
        loginErrorMessage = null
        loginSuccess = false
        // Opcional: limpar e-mail e senha
        // userResponseEmail = ""
        // userResponsePassword = ""
    }
}