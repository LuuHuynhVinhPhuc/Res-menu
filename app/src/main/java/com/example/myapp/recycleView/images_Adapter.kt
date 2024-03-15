package com.example.myapp.recycleView

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.normalSlider.ImageItem
import com.example.myapp.normalSlider.viewPager_Adapter
import com.example.myapp.normal_Slider
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log

class images_Adapter(val context: Context) : RecyclerView.Adapter<images_Adapter.MyViewHolder>()
{
    private var programs: List<program_Item> = listOf()
    private val snapHelper = LinearSnapHelper()
    fun setData(programs: List<program_Item>) {
        this.programs = programs
        notifyDataSetChanged()
    }

    fun attachSnapHelper(recyclerView: RecyclerView) {
        snapHelper.attachToRecyclerView(recyclerView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_layout_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val program = programs[position]

        // Set program title
        holder.tvProgram.text = program.program

        // Format and set date-time range
        val startDate = formatDate(program.start_date)
        val endDate = formatDate(program.end_date)

        holder.tvDateTime.text = "Từ ngày $startDate đến ngày $endDate"

        holder.idEvent.text = program.eventID

        // Load and display images
        for (i in 0 until program.images.size) {
            val imageLink = program.images[i].image_link
            Glide.with(context)
                .load("file:///android_asset/$imageLink")
                .placeholder(R.drawable.ct1)
                .error(R.drawable.ct1b)
                .into(holder.imageViews[i])

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViews: Array<ImageView> = arrayOf(
            itemView.findViewById(R.id.menu),
            itemView.findViewById(R.id.menu2),
            itemView.findViewById(R.id.menu3),
            itemView.findViewById(R.id.slider1),
            itemView.findViewById(R.id.slider2),
            itemView.findViewById(R.id.slider3)
        )
        val tvProgram: TextView = itemView.findViewById(R.id.eventName)
        val tvDateTime: TextView = itemView.findViewById(R.id.dateTime)
        val idEvent: TextView = itemView.findViewById(R.id.idEvent)
    }
    private fun formatDate(dateString: String): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Adjust format as needed
        val date = formatter.parse(dateString)
        val newFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Customize output format
        return newFormatter.format(date)
    }
}