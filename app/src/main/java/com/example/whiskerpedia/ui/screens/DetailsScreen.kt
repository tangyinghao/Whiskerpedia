package com.example.whiskerpedia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whiskerpedia.viewmodel.WhiskerpediaViewModel

@Composable
fun DetailScreen(
    whiskerpediaViewModel: WhiskerpediaViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val image = whiskerpediaViewModel.selectedImage

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
            Text(
                text = "ID: ${image.id}",
                style = MaterialTheme.typography.titleLarge
            )
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
