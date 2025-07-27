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

    fun onPasswordCurrentChange(newCurrentPassword: String) {
        _uiState.update { it.copy(userResponseRegisterPasswordCurrent = newCurrentPassword) }
        _uiState.update { it.copy(createUserErrorMessage = null) }
    }

    //Utilizando essa logica para criar usuário
    fun newPerformClick() {
        val name = _uiState.value.userResponseRegisterName
        val email = _uiState.value.userResponseRegisterEmail
        val password = _uiState.value.userResponseRegisterPassword
        val passwordCurrent = _uiState.value.userResponseRegisterPasswordCurrent

        if (password != passwordCurrent) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    createUserErrorMessage = "As senhas não coincidem."
                )
            }
        } else if (password.length < 6) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    createUserErrorMessage = "A senha deve ter no mínimo 6 caracteres"
                )
            }
        } else {

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
                                createUserSuccess = true,
                            )
                        }
                        resetRegisterState()
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                createUserSuccess = false,
                                createUserErrorMessage = "Erro ao criar conta, tente novamente"
                            )
                        }
                        resetRegisterState()
                    }
                }
        }
    }

    fun resetRegisterState() {
        _uiState.update {
            it.copy(
                userResponseRegisterName = "",
                userResponseRegisterEmail = "",
                userResponseRegisterPassword = "",
                userResponseRegisterPasswordCurrent = ""
            )
        }
    }
}