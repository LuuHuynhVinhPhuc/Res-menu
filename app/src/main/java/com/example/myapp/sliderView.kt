package com.example.myapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.widget.Toast
import androidx.core.view.MotionEventCompat
import androidx.core.widget.AutoScrollHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.autoSlider.AutoImage_Item
import com.example.myapp.autoSlider.AutoProgram_Item
import com.example.myapp.autoSlider.AutoSlider_Adapter
import com.example.myapp.recycleView.images_Adapter
import com.example.myapp.recycleView.program_Item
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.util.Timer
import java.util.TimerTask

class sliderView : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AutoSlider_Adapter
    private var programs: List<AutoProgram_Item> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_slider_view)

        // set variables
        recyclerView = findViewById(R.id.autoImageSlider)
        adapter = AutoSlider_Adapter(this)
        // read Json data
        readjsondata()
        // load data from share references
        loadData()
        // set recycler view
        RVset()
    }

    private fun RVset(){
        recyclerView.adapter = adapter
        adapter.setData(programs)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager


        // snapHelper for focus elements
        adapter.attachSnapHelper(recyclerView)

        // set timer
        val timer = Timer()

//        timer.schedule(object : TimerTask() {
//            override fun run() {
//                val layoutManager = recyclerView.layoutManager ?: return  // Handle missing layoutManager
//
//                val lastVisibleItem = layoutManager.f
//                //val totalItemCount = autoScrollAdapter.itemCount - 1  // Pre-calculate total count
//
//                if (lastVisibleItem < totalItemCount) {
//                    recyclerView.smoothScrollToPosition(lastVisibleItem + 1)
//                } else {
//                    // Loop back to the first item with smooth scrolling
//                    recyclerView.smoothScrollToPosition(0)
//                }
//            }
//        }, 0, 5000)  // Initial delay (0) and scroll interval (5 seconds)


    }
    private fun loadData() {
        val sharedPreferences : SharedPreferences = getSharedPreferences("id_Event", Context.MODE_PRIVATE)
        val savedString : String? = sharedPreferences.getString("String_key", null)


    }

    private fun readjsondata() {
        // read json files
        val json = Json{ignoreUnknownKeys = true}
        val jsonString = assets.open("digitalmkt.json").bufferedReader().readText()

        val jsonElement: JsonElement = Json.parseToJsonElement(jsonString)
        programs = json.decodeFromString<List<AutoProgram_Item>>(jsonElement.toString())
    }


    // Handle touch events and navigate to the next page
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val intent = Intent(this, normal_Slider::class.java)
            startActivity(intent)
            return true // Consume the event to prevent further handling
        }
        return super.onTouchEvent(event)
    }

}
