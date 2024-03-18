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
import com.example.myapp.recycleView.program_Item
import java.io.File
import java.net.MalformedURLException
import java.net.URLEncoder
import java.net.URL

class viewPager_Adapter(val context: Context): RecyclerView.Adapter<viewPager_Adapter.ViewHolder>(){

    private var programs: List<imagesNormal_Item> = listOf()
    fun setData(programs: List<imagesNormal_Item>) {
        this.programs = programs
        notifyDataSetChanged()
    }

    class ViewHolder(iteView: View): RecyclerView.ViewHolder(iteView){
        val imageView = iteView.findViewById<ImageView>(R.id.imgNormalSlider)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.image_item_layout,parent,false)
        )
    }
    override fun getItemCount(): Int {
        return programs.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = programs[position]

        val imageUrl = "file:///android_asset/${imageItem.image_link}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ct1)
            .error(R.drawable.ct1b)
            .into(holder.imageView)
    }
}