package com.example.app_orcamind.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserRegistration(
   private val auth: FirebaseAuth
) {
    suspend fun registerUser(email: String, password: String): Result<Unit> {
       return try {
           auth.createUserWithEmailAndPassword(email,password).await()
           Result.success(Unit)
       } catch (e: Exception) {
           Result.failure(e)
       }
    }

}