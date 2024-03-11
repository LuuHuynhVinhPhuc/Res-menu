package com.example.myapp.recycleView

import kotlinx.serialization.Serializable
@Serializable
data class program_Item(
    val program: String,
    val images: List<images_Item>
)
