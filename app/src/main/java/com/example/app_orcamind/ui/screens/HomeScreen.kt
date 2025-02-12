package com.example.app_orcamind.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.R


@Composable
fun HomeScreen() {

    val context = LocalContext.current

    // Lista de itens com nomes e imagens diferentes
    val itemsList = listOf(
        "Item 1" to R.drawable.ic_launcher_foreground,
        "Item 2" to R.drawable.ic_launcher_foreground,
        "Item 3" to R.drawable.ic_launcher_foreground,
        "Item 4" to R.drawable.ic_launcher_foreground,
        "Item 5" to R.drawable.ic_launcher_foreground,
        "Item 6" to R.drawable.ic_launcher_foreground,
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.systemBars) // Respeita a StatusBar e NavigationBar

    ) {
        items(itemsList) { (text, image) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = "Imagem",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = text,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

