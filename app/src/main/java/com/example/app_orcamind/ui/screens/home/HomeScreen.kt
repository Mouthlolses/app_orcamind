package com.example.app_orcamind.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.ui.components.HomeCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
    ) {
        HomeScreenLayout()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLayout() {
    val scrollState = rememberLazyListState()

    Scaffold(
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
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                TopAppBar(
                    title = { Text("OrcaMind") },
                    actions = {
                        TextButton(onClick = {}) {
                            Text("Home", color = Color.Black)
                        }
                        TextButton(onClick = {}) {
                            Text("Details", color = Color.Black)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HomeCard()
            }
            item {
                HomeCard()
            }
            item {
                HomeCard()
            }
            item {
                HomeCard()
            }
            item {
                HomeCard()
            }
            item {
                HomeCard()
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}