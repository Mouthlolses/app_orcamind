package com.example.app_orcamind.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {


    //Padrão encapsulamento de estado
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    // Instância do Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val _userResponseRegisterEmail = MutableStateFlow("")
    val userResponseRegisterEmail: StateFlow<String> = _userResponseRegisterEmail

    private val _userResponseRegisterPassword = MutableStateFlow("")
    val userResponseRegisterPassword: StateFlow<String> = _userResponseRegisterPassword

    private val _isLoading = MutableStateFlow(false)

    private val _createUserErrorMessage = MutableStateFlow<String?>(null)
    val createUserErrorMessage: StateFlow<String?> = _createUserErrorMessage

    private val _createUserSuccess = MutableStateFlow(false)


    fun onNameChange(newName: String) {
        _uiState.update { it.copy(userResponseRegisterName = newName) }
        _uiState.update { it.copy(createUserErrorMessage = null) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(userResponseRegisterEmail = newEmail) }
        // Limpa mensagens de erro ao digitar novamente
        _uiState.update { it.copy(createUserErrorMessage = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(userResponseRegisterPassword = newPassword) }
        _uiState.update { it.copy(createUserErrorMessage = null) }
    }

    fun performCreateUser() {
        _isLoading.value = true
        _createUserErrorMessage.value = null
        _createUserSuccess.value = false

        if (_userResponseRegisterEmail.value.isBlank() || _userResponseRegisterPassword.value.isBlank()) {
            _createUserErrorMessage.value = "Por favor, preencha todos os campos"
            _isLoading.value = false
            return
        }
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(
                    _userResponseRegisterEmail.value,
                    _userResponseRegisterPassword.value
                )
                    .await()
                _createUserSuccess.value = true

                Log.i("createFirebase", "Create User Success")

            } catch (e: FirebaseAuthException) {
                _createUserSuccess.value = false
                _createUserErrorMessage.value = when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "O e-mail digitado é inválido."
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "Este e-mail já está em uso."
                    "ERROR_WEAK_PASSWORD" -> "Senha muito fraca."
                    // Adicione mais casos conforme necessário para mensagens mais amigáveis
                    else -> "Erro de cadastro: ${e.message}"
                }
            } catch (e: Exception) {
                // Erro genérico
                _createUserSuccess.value = false
                _createUserErrorMessage.value = "Erro desconhecido ao se registrar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    //Utilizando essa logica para criar usuário
    fun newPerformClick() {
        val name = _uiState.value.userResponseRegisterName
        val email = _uiState.value.userResponseRegisterEmail
        val password = _uiState.value.userResponseRegisterPassword

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    createUserErrorMessage = "Por favor, preencha todos os campos."
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true,
                createUserErrorMessage = null,
                createUserSuccess = false
            )
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            createUserSuccess = true
                        )
                    }
                    resetRegisterState()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            createUserSuccess = false,
                            createUserErrorMessage = task.exception?.message
                        )
                    }
                    resetRegisterState()
                }
            }
    }

    fun resetRegisterState() {
        _uiState.update {
            it.copy(
                userResponseRegisterName = "",
                userResponseRegisterEmail = "",
                userResponseRegisterPassword = "",
            )
        }
    }
}