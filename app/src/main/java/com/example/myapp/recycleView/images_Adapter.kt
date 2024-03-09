package com.example.myapp.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

class images_Adapter constructor(private val imageList : List<images_Item>) :
    RecyclerView.Adapter<images_Adapter.MyViewHolder>()
{
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val images : ImageView = itemView.findViewById(R.id.rv_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.images.setImageResource(imageList[position].image)
    }
}