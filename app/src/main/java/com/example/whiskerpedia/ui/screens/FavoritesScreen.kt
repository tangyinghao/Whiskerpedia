package com.example.whiskerpedia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.whiskerpedia.models.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.whiskerpedia.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    uiState: UiState,
    onListItemClicked: (Image) -> Unit,
    favorites: List<Image>,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading favorite cats.")
            }
        }
        is UiState.Success -> {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
            ) {
                items(favorites) { image ->
                    CatCard(
                        image = image,
                        onClick = { onListItemClicked(image) }
                    )
                }
            }
        }
    }
}