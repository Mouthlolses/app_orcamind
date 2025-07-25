package com.example.app_orcamind.ui.screens.home

import androidx.compose.ui.graphics.Color

data class HomeUiState(
    val userName: String,
    val category: String,
    val value: Float,
    val color: Color
)

val categorias = listOf(
    HomeUiState("Matheus","Alimentação", 500f, Color(0xFF4CAF50)),
)