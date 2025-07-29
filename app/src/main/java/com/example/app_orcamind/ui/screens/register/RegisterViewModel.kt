package com.example.app_orcamind.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_orcamind.data.repository.UserRegistration
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRegistration: UserRegistration
) : ViewModel() {

    //Padrão encapsulamento de estado
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


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
            return
        }

        if (password.length < 6) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    createUserErrorMessage = "A senha deve ter no mínimo 6 caracteres"
                )
            }
            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    createUserErrorMessage = null,
                    createUserSuccess = false
                )
            }

            val result = userRegistration.registerUser(email,password)

            if (result.isSuccess) {
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
                        createUserErrorMessage = result.exceptionOrNull()?.message ?: "Erro ao criar conta"
                    )
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