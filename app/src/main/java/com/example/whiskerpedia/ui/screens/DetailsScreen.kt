package com.example.whiskerpedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whiskerpedia.viewmodel.UiState
import com.example.whiskerpedia.viewmodel.WhiskerpediaViewModel
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    uiState: UiState,
    whiskerpediaViewModel: WhiskerpediaViewModel,
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
                Text("Failed to load image details.")
            }
        }

        is UiState.Success -> {
            val image = whiskerpediaViewModel.selectedImage
            val favoriteCats = whiskerpediaViewModel.favoriteCats
            val breed = whiskerpediaViewModel.allBreeds.find { it.referenceImageId == image?.id }
            val isFavorite = favoriteCats.any { it.id == image?.id }

            if (image != null) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = image.url,
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ID: ${image.id}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                if (isFavorite) {
                                    whiskerpediaViewModel.removeFromFavorites(image)
                                } else {
                                    whiskerpediaViewModel.addToFavorites(image)
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = if (isFavorite)
                                    Color(0xFFB00020).copy(alpha = 0.85f)
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "No image selected.",
                    modifier = modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}