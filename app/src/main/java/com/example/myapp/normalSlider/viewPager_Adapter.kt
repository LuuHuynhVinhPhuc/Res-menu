package com.example.myapp.normalSlider

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

class viewPager_Adapter: ListAdapter<ImageItem,viewPager_Adapter.ViewHolder>(DiffCallback()){

    class DiffCallback : DiffUtil.ItemCallback<ImageItem>(){
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }
    class ViewHolder(iteView: View): RecyclerView.ViewHolder(iteView){
        private val imageView = iteView.findViewById<ImageView>(R.id.imgNormalSlider)

        fun bindData(item: ImageItem){
            try {
                // Sử dụng chuỗi đã thêm giao thức trong Glide
                Glide.with(itemView)
                    .load(item.url)
                    .into(imageView)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bindData(imageItem)
    }
}