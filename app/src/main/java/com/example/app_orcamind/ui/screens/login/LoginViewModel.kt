package com.example.app_orcamind.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

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

    // Instância do Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // --- Funções para atualizar e-mail e senha ---
    fun updateUserEmail(newEmail: String) {
        _userResponseEmail.value = newEmail
        // Limpa mensagens de erro ao digitar novamente
        _loginErrorMessage.value = null
    }

    fun updateUserPassword(newPassword: String) {
        _userResponsePassword.value = newPassword
        _loginErrorMessage.value = null
    }

    fun performLogin() {
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

                auth.signInWithEmailAndPassword(_userResponseEmail.value,_userResponsePassword.value)
                    .await()
                // Se chegou aqui, o login foi bem-sucedido
                _loginSuccess.value = true
                Log.i("authFirebase","Login Success")

            } catch (e: Exception){
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

    fun resetLoginState() {
        _isLoading.value = false
        _loginErrorMessage.value = null
        _loginSuccess.value = false
        // Opcional: limpar e-mail e senha
        // userResponseEmail = ""
        // userResponsePassword = ""
    }
}