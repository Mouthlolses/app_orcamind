package com.example.app_orcamind.data.repository

import com.example.app_orcamind.data.datasource.FirebaseAuthDataSource
import javax.inject.Inject

class UserRegistration @Inject constructor(
    private val dataSource: FirebaseAuthDataSource
) {
    suspend fun registerUser(email: String, password: String): Result<Unit> {
        return dataSource.createUser(email, password)
    }
}