package com.example.app_orcamind.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.R


@Composable
fun HomeCard() {
    val paddingCardHome = dimensionResource(R.dimen.padding_cardhome)
    Card(
        modifier = Modifier
            .padding(paddingCardHome),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(paddingCardHome),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingCardHome)
        ) {
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Text(
                text = stringResource(R.string.welcome),
                style = typography.displayMedium
            )
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Text(
                text = stringResource(R.string.access),
                modifier = Modifier
                    .padding(top = 12.dp),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
        }
    }
}