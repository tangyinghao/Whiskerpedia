package com.example.whiskerpedia.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.whiskerpedia.R
import com.example.whiskerpedia.viewmodel.WhiskerpediaViewModel

enum class WhiskerpediaScreen(@StringRes val title: Int) {
    List(R.string.app_name),
    Detail(R.string.cat_details)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiskerpediaAppBar(
    currentScreen: WhiskerpediaScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    whiskerpediaViewModel: WhiskerpediaViewModel
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title),
                modifier = Modifier.padding(start = 4.dp)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (currentScreen == WhiskerpediaScreen.List) {
                var menuExpanded by remember { mutableStateOf(false) }

                IconButton(onClick = {
                    menuExpanded = !menuExpanded
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Open menu to select different "
                    )
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
    )
}

@Composable
fun WhiskerpediaApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = when (backStackEntry?.destination?.route?.substringBefore("/")) {
        WhiskerpediaScreen.List.name -> WhiskerpediaScreen.List
        WhiskerpediaScreen.Detail.name -> WhiskerpediaScreen.Detail
        //WhiskerpediaScreen.Favorites.name -> WhiskerpediaScreen.Favorites
        else -> WhiskerpediaScreen.List
    }

    val whiskerpediaViewModel: WhiskerpediaViewModel = viewModel(factory = WhiskerpediaViewModel.Factory)

    Scaffold(
        topBar = {
            WhiskerpediaAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                whiskerpediaViewModel = whiskerpediaViewModel,
//                onFavoritesClick = {
//                    navController.navigate(WhiskerpediaScreen.Favorites.name)
//                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WhiskerpediaScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = WhiskerpediaScreen.List.name) {
                ListScreen(
                    uiState = whiskerpediaViewModel.uiState,
                    onListItemClicked = { image ->
                        whiskerpediaViewModel.setSelectedCat(image)
                        navController.navigate(WhiskerpediaScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = WhiskerpediaScreen.Detail.name) {
                DetailsScreen(
                    whiskerpediaViewModel = whiskerpediaViewModel,
                    //selectedWhiskerpediaUiState = whiskerpediaViewModel.selectedWhiskerpediaUiState,
                    modifier = Modifier,
                    navController = navController
                )
            }
//            composable(route = WhiskerpediaScreen.Favorites.name) {
//                FavoritesScreen(
//                    favorites = whiskerpediaViewModel.favoriteCats,
//                    onWhiskerpediaClicked = { image  ->
//                        whiskerpediaViewModel.setSelectedWhiskerpedia(image)
//                        navController.navigate(WhiskerpediaScreen.Detail.name)
//                    },
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                )
//            }
        }
    }
}
