package com.example.whiskerpedia.database

import com.example.whiskerpedia.models.Breed
import com.example.whiskerpedia.models.Image
import com.example.whiskerpedia.network.ApiService

class Repository(private val apiService: ApiService) {

    suspend fun getCatImages(limit: Int = 10): List<Image> {
        return apiService.getImages(limit = limit)
    }

    suspend fun getBreeds(): List<Breed> {
        return apiService.getBreeds()
    }

    suspend fun getCatImagesByBreed(breedId: String, limit: Int = 10): List<Image> {
        return apiService.getImages(limit = limit, breedId = breedId)
    }

}
