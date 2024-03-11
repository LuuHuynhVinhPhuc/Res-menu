package com.example.myapp.recycleView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.R

class images_Adapter constructor(private val context: Context) :
    RecyclerView.Adapter<images_Adapter.MyViewHolder>()
{
    private var program: List<program_Item> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_layout_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return program.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val program = program[position]

        holder.tvProgram.text = program.program
        // Load and display image (replace with your image loading library)
        Glide.with(context).load(context.assets.open(program.images[1].image_link)).into(holder.images)
    }
    fun setData(programs: List<program_Item>) {
        this.program = programs
        notifyDataSetChanged()
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val images : ImageView = itemView.findViewById(R.id.rv_imageView)
        var tvProgram : TextView = itemView.findViewById(R.id.eventName)
    }
}