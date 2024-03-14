package com.example.myapp.autoSlider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.AutoScrollHelper
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.normalSlider.ImageItem
import com.example.myapp.recycleView.program_Item
import java.net.MalformedURLException

class AutoSlider_Adapter(private val context: Context) : RecyclerView.Adapter<AutoSlider_Adapter.MyViewHolder>()
{
    private var programs: List<AutoProgram_Item> = listOf()
    private val snapHelper = LinearSnapHelper()

    fun setData(programs: List<AutoProgram_Item>) {
        this.programs = programs
        notifyDataSetChanged()
    }

    fun attachSnapHelper(recyclerView: RecyclerView) {
        snapHelper.attachToRecyclerView(recyclerView)
    }
    class MyViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        val imageSlider: ImageView = itemView.findViewById(R.id.autoImageSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.auto_slider, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val program2 = programs[position]


    }

}