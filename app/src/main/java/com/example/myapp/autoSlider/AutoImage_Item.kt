package com.example.myapp.autoSlider

import kotlinx.serialization.Serializable
@Serializable
data class AutoImage_Item(
    val image_link: String,
    val description: String,
    val type : String
)

