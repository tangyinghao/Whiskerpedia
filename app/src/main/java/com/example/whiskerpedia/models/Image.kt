package com.example.whiskerpedia.models

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed> = emptyList()
) : java.io.Serializable