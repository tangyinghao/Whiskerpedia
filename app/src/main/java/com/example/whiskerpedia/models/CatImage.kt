package com.example.whiskerpedia.models

import kotlinx.serialization.Serializable

@Serializable
data class CatImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed> = emptyList()
)