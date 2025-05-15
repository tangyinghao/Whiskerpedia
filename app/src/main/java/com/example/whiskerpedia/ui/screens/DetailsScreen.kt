package com.example.whiskerpedia.ui.screens

import android.R.attr.opacity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whiskerpedia.viewmodel.WhiskerpediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    whiskerpediaViewModel: WhiskerpediaViewModel,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val favoriteCats = whiskerpediaViewModel.favoriteCats // Now getting the favoriteMovies state

    val image = whiskerpediaViewModel.selectedImage
    val breed = whiskerpediaViewModel.allBreeds.find { it.referenceImageId == image?.id}
    val isFavorite = favoriteCats.any { it.id == breed?.id }

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
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "ID: ${image.id}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = {
                    if (isFavorite) {
                        whiskerpediaViewModel.removeFromFavorites(breed)
                    } else {
                        whiskerpediaViewModel.addToFavorites(breed)
                    }
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.White
                    )
                }
            }
        }
    } else {
        // fallback UI if null
        Text(
            text = "No image selected.",
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}