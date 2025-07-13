package com.example.app_orcamind.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val email: String,
    val password: String
)
