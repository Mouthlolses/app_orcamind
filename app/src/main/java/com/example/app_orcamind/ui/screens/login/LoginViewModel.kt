package com.example.app_orcamind.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_orcamind.data.repository.UserRegistration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRegistration
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _navigateToHome = MutableSharedFlow<Unit>()
    val navigateToHome: SharedFlow<Unit> = _navigateToHome

    fun updateUserEmail(newEmail: String) {
        _uiState.update { it.copy(userResponseEmail = newEmail) }
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

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    loginErrorMessage = null,
                )
            }

            val result = userRepository.authUser(email, password)

            if (result.isSuccess) {
                Log.i("Login", "Sucesso")
                _navigateToHome.emit(Unit)
            } else {
                _uiState.update {
                    Log.i("Login", "Falha no login")
                    it.copy(
                        isLoading = false,
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