package com.example.app_orcamind.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.R


@Composable
fun HomeCard(
    userName: String,
    revenue: String,
    expense: String,
    balance: String,
    economy: String
) {

    val balanceFloat = balance.toFloatOrNull() ?: 0f
    val economyFloat = economy.toFloatOrNull()?.takeIf { it > 0f } ?: 1f // evita divisão por zero
    val progress = (balanceFloat / economyFloat).coerceIn(0f, 1f)


    val paddingCardHome = dimensionResource(R.dimen.padding_cardhome)
    Card(
        modifier = Modifier
            .padding(paddingCardHome),
        colors = CardDefaults.cardColors(
            Color.White,
            Color.Black,
            Color.Gray,
            Color.Gray,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(paddingCardHome),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingCardHome)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(Color.DarkGray)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.Start),
                text = "Olá, $userName",
                style = typography.titleSmall,
                color = Color.White
            )
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Text(
                text = stringResource(R.string.resume),
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 16.dp),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Receitas", style = typography.labelLarge)
                    Text(
                        "R$ $revenue",
                        style = typography.bodyLarge,
                        color = Color(0xFF388E3C)
                    ) // verde
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Despesas", style = typography.labelLarge)
                    Text(
                        "R$ $expense",
                        style = typography.bodyLarge,
                        color = Color(0xFFD32F2F)
                    ) // vermelho
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Saldo", style = typography.labelLarge)
                    Text(
                        "R$ $balance",
                        style = typography.bodyLarge,
                        color = Color(0xFF1976D2)
                    ) // azul
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Meta de economia: R$ $economy", style = typography.bodyMedium)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = ProgressIndicatorDefaults.linearColor,
                trackColor = ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

            if (balance > economy) {
                Text(
                    "Você já economizou o suficiente este mês. parabéns!",
                    style = typography.labelSmall
                )
            }

            if (balance < economy) {
                Text("Você já economizou R$ $balance este mês!", style = typography.labelSmall)
            }
        }
    }
}


@Preview
@Composable
fun HomeCardPreview() {
    HomeCard(
        balance = "1500",
        economy = "500",
        revenue = "2500",
        expense = "1000",
        userName = "Matheus"
    )
}