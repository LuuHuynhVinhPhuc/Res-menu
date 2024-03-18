package com.example.myapp.autoSlider

import com.example.myapp.recycleView.images_Item
import kotlinx.serialization.Serializable
@Serializable
data class AutoProgram_Item(
    val program: String,
    val images: List<AutoImage_Item>,
    val start_date : String,
    val end_date : String,
    val eventID : String
)
