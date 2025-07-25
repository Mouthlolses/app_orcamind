package com.example.app_orcamind.domain.repository

import com.example.app_orcamind.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {
    suspend fun saveUserToFirestore(user: User) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("Usuário não autenticado")

        Firebase.firestore.collection("users")
            .document(uid)
            .set(user)
            .await()
    }
}