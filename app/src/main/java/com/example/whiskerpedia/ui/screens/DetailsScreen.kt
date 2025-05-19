package com.example.whiskerpedia.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
            val isFavorite = favoriteCats.any { it.id == image?.id }
            val context = LocalContext.current

            if (image != null) {
                val breed = image.breeds.firstOrNull()

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
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
                            text = breed?.name ?: "Unknown Breed",
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
                    breed?.let {
                        it.wikipediaUrl?.let { url ->
                            if (url.isNotEmpty()) {
                                val Url = "$url"
                                Spacer(modifier = Modifier.height(8.dp))
                                LinkCard(
                                    text = "More Info",
                                    modifier = Modifier.align(Alignment.Start)
                                ) {
                                    val intent = Intent(Intent.ACTION_VIEW, Url.toUri())
                                    context.startActivity(intent)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("Origin: ${it.origin}", style = MaterialTheme.typography.bodyMedium)
                            Text("Life Span: ${it.lifeSpan} years", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "Weight: ${it.weight.metric} kg (${it.weight.imperial} lb)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            it.temperament.split(", ").forEach { trait ->
                                AssistChip(
                                    onClick = {},
                                    label = {
                                        Text(
                                            trait,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    modifier = Modifier.height(28.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            it.description,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Clip
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (whiskerpediaViewModel.relatedImages.isNotEmpty()) {
                            Text(
                                text = "More images of this breed",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.align(Alignment.Start)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(whiskerpediaViewModel.relatedImages) { relatedImage ->
                                    AsyncImage(
                                        model = relatedImage.url,
                                        contentDescription = "More Cat Image",
                                        modifier = Modifier
                                            .size(120.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }

                    } ?: run {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Breed information not available.",
                            style = MaterialTheme.typography.bodyMedium
                        )
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


@Composable
fun LinkCard(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .defaultMinSize(minWidth = 120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


