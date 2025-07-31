package com.example.app_orcamind.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_orcamind.ui.components.ConfigureCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = viewModel()
) {
    val uiState by homeScreenViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
    ) {
        HomeScreenLayout( 
            revenue = uiState.revenue,
            expense = uiState.expense,
            balance = uiState.balance,
            revenueValue = {homeScreenViewModel.onRevenueChanged(it)},
            expenseValue = {homeScreenViewModel.onExpenseChanged(it)},
            balanceValue = {homeScreenViewModel.onBalanceChanged(it)}
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLayout(
    revenue: String,
    expense: String,
    balance: String,
    revenueValue: (String) -> Unit,
    expenseValue: (String) -> Unit,
    balanceValue: (String) -> Unit
) {
    val scrollState = rememberLazyListState()
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text("Infome seus dados financeiros")
            },
            text = @Composable {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = revenue,
                        onValueChange = revenueValue,
                        label = { Text("Receita") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = expense,
                        onValueChange = expenseValue,
                        label = { Text("Despesa") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = balance,
                        onValueChange = balanceValue,
                        label = { Text("Saldo") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {showDialog = false}) {
                    Text(text = "FECHAR")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF6200EA), Color(0xFFFEFFFE))
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Surface(
                    tonalElevation = 4.dp, // Dá elevação como sombra
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    TopAppBar(
                        title = { Text("OrcaMind") },
                        actions = {
                            TextButton(onClick = {}) {
                                Text(
                                    "Conta",
                                    modifier = Modifier
                                        .clip(shapes.large)
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    Color(0xFF6200EA), Color(0xFFAA00FF)
                                                )
                                            )
                                        )
                                        .padding(horizontal = 10.dp, vertical = 4.dp),
                                    color = Color.White,
                                    style = typography.titleSmall,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            titleContentColor = Color.Black
                        )
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    HomeCard()
                }
                item {
                    ConfigureCard(onClick = {
                        showDialog = true
                    })
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}