package com.example.app_orcamind.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.R


@Preview
@Composable
fun TipCard() {
    val paddingCardHome = dimensionResource(R.dimen.padding_cardhome)

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            Color.Black,
            Color.Gray,
            Color.Gray,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(paddingCardHome)
        ) {
            Row {
                Text(text = "Dica 1")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Dica 2")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Dica 3")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Dica 4")
            }
        }
    }
}