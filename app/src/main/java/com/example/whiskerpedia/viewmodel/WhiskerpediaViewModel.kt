package com.example.whiskerpedia.viewmodel

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
import com.example.whiskerpedia.models.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UiState {
    data class Success(val images: List<Image>) : UiState
    object Error : UiState
    object Loading : UiState
}

class WhiskerpediaViewModel(private val repository: Repository) : ViewModel() {

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val result = repository.getCatImages()
                uiState = UiState.Success(result)
            } catch (e: IOException) {
                uiState = UiState.Error
            } catch (e: HttpException) {
                uiState = UiState.Error
            }
        }
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
