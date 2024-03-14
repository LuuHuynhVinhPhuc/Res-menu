package com.example.myapp.normalSlider

import com.example.myapp.recycleView.images_Item
import kotlinx.serialization.Serializable
import java.net.URL
@Serializable
data class ImageItem(
    val program: String,
    val images: List<imagesNormal_Item>,
    val start_date : String,
    val end_date : String,
    val eventID : String
)
