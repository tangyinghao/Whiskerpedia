package com.example.whiskerpedia.network

import com.example.whiskerpedia.models.Breed
import com.example.whiskerpedia.models.Image
import com.example.whiskerpedia.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search")
    suspend fun getImages(
        @Query("limit") limit: Int = 10,
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("mime_types") mimeTypes: String = "jpg",
        @Query("order") order: String = "RANDOM",
        @Query("breed_id") breedId: String? = null,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): List<Image>

    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): List<Breed>
}
