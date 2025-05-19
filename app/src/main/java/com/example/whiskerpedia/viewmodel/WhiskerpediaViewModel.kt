package com.example.whiskerpedia.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.example.whiskerpedia.WhiskerpediaApp
import com.example.whiskerpedia.database.Repository
import com.example.whiskerpedia.models.Breed
import com.example.whiskerpedia.models.Image
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UiState {
    data class Success(val images: List<Image>) : UiState
    object Error : UiState
    object Loading : UiState
}

open class WhiskerpediaViewModel(private val repository: Repository) : ViewModel() {

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    var selectedImage: Image? = null
        private set

    var relatedImages by mutableStateOf(listOf<Image>())
        private set

    var allBreeds: List<Breed> by mutableStateOf(emptyList())
        private set

    open var favoriteCats: List<Image> by mutableStateOf(emptyList())
        private set

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val result = repository.getCatImages()
                Log.d("Whiskerpedia", "Fetched images: ${result.size}")
                uiState = UiState.Success(result)
            } catch (e: IOException) {
                Log.e("Whiskerpedia", "IOException: ${e.message}")
                uiState = UiState.Error
            } catch (e: HttpException) {
                Log.e("Whiskerpedia", "HttpException: ${e.code()}")
                uiState = UiState.Error
            }
        }
    }

    fun fetchRelatedImages() {
        val breedId = selectedImage?.breeds?.firstOrNull()?.id ?: return

        viewModelScope.launch {
            try {
                relatedImages = repository.getCatImagesByBreed(breedId)
            } catch (e: Exception) {
                Log.e("Whiskerpedia", "Failed to fetch related images: ${e.message}")
            }
        }
    }

    fun setSelectedCat(image: Image) {
        selectedImage = image
        fetchRelatedImages()
    }

    fun addToFavorites(image: Image) {
        if (favoriteCats.none { it.id == image.id }) {
            favoriteCats = (favoriteCats + image)
        }
    }

    fun removeFromFavorites(image: Image) {
        favoriteCats = favoriteCats.filterNot { it.id == image.id }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WhiskerpediaApp
                val repository = Repository(application.container.apiService)
                WhiskerpediaViewModel(repository)
            }
        }
    }
}
