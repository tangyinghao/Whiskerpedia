package com.example.whiskerpedia.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.whiskerpedia.models.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favorites: List<Image>,
    onListItemClicked: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(favorites) { image ->
            CatCard(
                image = image,
                modifier = Modifier.padding(8.dp),
                onClick = { onListItemClicked(image) }
            )
        }
    }
}