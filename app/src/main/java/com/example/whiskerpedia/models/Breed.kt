package com.example.whiskerpedia.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    val description: String,
    @SerialName("life_span") val lifeSpan: String,
    @SerialName("wikipedia_url") val wikipediaUrl: String? = null,
    @SerialName("reference_image_id") val referenceImageId: String? = null,
    val weight: Weight,
    val temperament: String
)