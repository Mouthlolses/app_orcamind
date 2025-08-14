package com.example.app_orcamind.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_orcamind.R


@Composable
fun TipCard(
    revenueValue: String
) {

    val paddingCardHome = dimensionResource(R.dimen.padding_cardhome)

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF76FF03),
            Color.White,
            Color.Gray,
            Color.Gray,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(paddingCardHome)
        ) {
            Row() {
                Text(
                    text = stringResource(R.string.revenue),
                    modifier = Modifier
                        .clip(ShapeDefaults.Medium)
                        .background(Color.DarkGray)
                        .padding(top = 4.dp, bottom = 6.dp)
                        .padding(horizontal = 12.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                Text(
                    text = revenueValue,
                    fontSize = 34.sp,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TipCardPreview() {
    TipCard(
        revenueValue = "R$ 2.000,00"
    )
}