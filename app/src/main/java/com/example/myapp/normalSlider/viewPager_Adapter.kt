package com.example.myapp.normalSlider

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.R
import java.io.File
import java.net.MalformedURLException
import java.net.URLEncoder
import java.net.URL

class viewPager_Adapter(val context: Context, val eventIDSF : String): ListAdapter<ImageItem,viewPager_Adapter.ViewHolder>(DiffCallback()){

    class DiffCallback : DiffUtil.ItemCallback<ImageItem>(){
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.eventID == newItem.eventID
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }
    class ViewHolder(iteView: View): RecyclerView.ViewHolder(iteView){
        val imageView = iteView.findViewById<ImageView>(R.id.imgNormalSlider)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        // Array to store up to 3 image URLs
        val imageUrls = mutableListOf<String>()

         //Load and display images
        for (i in 0 until 2) {
            val image = imageItem.images[i]

            // Kiểm tra nếu image_link không null và chứa "menu" hoặc "main" (không phân biệt chữ hoa/chữ thường)
            // và eventID của ImageItem tương ứng với eventIDSF
            if (!image.image_link.isNullOrBlank() &&
                ((image.image_link.equals("main.jpg", ignoreCase = true)) ||
                        (image.image_link.equals("menu1.jpg", ignoreCase = true) ||
                                image.image_link.equals("menu2.jpg", ignoreCase = true))) &&
                imageItem.eventID == eventIDSF
            ) {
                val imageUrl = "file:///android_asset/${image.image_link}"
                Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ct1)
                    .error(R.drawable.ct1b)
                    .into(holder.imageView)
                imageUrls.add(imageUrl)
                // Early exit if 3 images are found for efficiency
                if (imageUrls.size == 3) {
                    break
                }
            }
        }

    }
}