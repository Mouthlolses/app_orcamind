package com.example.app_orcamind.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    // Instância do Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    private val _userResponseEmail = MutableStateFlow("")
    val userResponseEmail: StateFlow<String> = _userResponseEmail

    private val _userResponsePassword = MutableStateFlow("")
    val userResponsePassword: StateFlow<String> = _userResponsePassword

    // --- Variáveis de estado para o processo de login ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginErrorMessage = MutableStateFlow<String?>(null)
    val loginErrorMessage: StateFlow<String?> = _loginErrorMessage
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess


    //  Funções para atualizar e-mail e senha
    fun updateUserEmail(newEmail: String) {
        _uiState.update { it.copy(userResponseEmail = newEmail) }
        // Limpa mensagens de erro ao digitar novamente
        _uiState.update { it.copy(loginErrorMessage = null) }
    }

    fun updateUserPassword(newPassword: String) {
        _uiState.update { it.copy(userResponsePassword = newPassword) }
        _uiState.update { it.copy(loginErrorMessage = null) }
    }

    fun newPerformClick() {
        val email = _uiState.value.userResponseEmail
        val password = _uiState.value.userResponsePassword

        if (email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    loginSuccess = false,
                    loginErrorMessage = "Por favor, preencha todos os campos."
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true,
                loginErrorMessage = null,
            )
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("Login", "Sucesso")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true
                        )
                    }
                    resetLoginState()
                } else {
                    _uiState.update {
                        Log.i("Login", "Falha no login")
                        it.copy(
                            isLoading = false,
                            loginSuccess = false,
                            loginErrorMessage = "Login ou senha incorretos"
                        )
                    }
                    resetLoginState()
                }
            }
    }


    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess
                } else {
                    onFailure(task.exception?.message ?: "Erro desconhecido")
                }
            }
    }


    fun resetLoginState() {
        _uiState.update {
            it.copy(
                userResponseEmail = "",
                userResponsePassword = "",
                isLoading = false,
                loginSuccess = false
            )
        }
    }
}