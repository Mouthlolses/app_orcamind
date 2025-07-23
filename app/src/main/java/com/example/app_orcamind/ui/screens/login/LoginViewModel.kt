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

    fun performLogin(
        email: String = _userResponseEmail.value,
        password: String = _userResponsePassword.value
    ) {
        _isLoading.value = true // Indica que o login está em andamento
        _loginErrorMessage.value = null // Limpa qualquer mensagem de erro anterior
        _loginSuccess.value = false // Reseta o status de sucesso

        if (_userResponseEmail.value.isBlank() || _userResponsePassword.value.isBlank()) {
            _loginErrorMessage.value = "Por favor, preencha todos os campos."
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            try {
                // A chamada .await() é a mágica das corrotinas com Firebase KTX.
                // Ele converte a Task assíncrona do Firebase (signInWithEmailAndPassword) em uma função suspend.
                // Ela "espera" a conclusão da operação do Firebase de forma não-bloqueante.

                auth.signInWithEmailAndPassword(email, password)
                    .await()
                // Se chegou aqui, o login foi bem-sucedido
                _loginSuccess.value = true
                Log.i("authFirebase", "Login Success")

            } catch (e: Exception) {
                _loginSuccess.value = false
                _loginErrorMessage.value = when (e) {
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
                _isLoading.value = false
            }
        }
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
                            loginErrorMessage = task.exception?.message
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