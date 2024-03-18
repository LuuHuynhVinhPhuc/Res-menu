package com.example.myapp.autoSlider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.normalSlider.viewPager_Adapter

class AutoSlider_Adapter(private val context: Context) : RecyclerView.Adapter<AutoSlider_Adapter.MyViewHolder>()
{
    private var programs: List<AutoImage_Item> = listOf()

    fun setData(programs: MutableList<AutoImage_Item>) {
        this.programs = programs
        notifyDataSetChanged()
    }

    class MyViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        val imageSlider: ImageView = itemView.findViewById(R.id.autoImageSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val program = programs[position]

        val imageUrl = "file:///android_asset/${program.image_link}"
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ct1)
            .error(R.drawable.ct1b)
            .into(holder.imageSlider)
    }

}