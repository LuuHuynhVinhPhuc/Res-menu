package com.example.myapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp.autoSlider.AutoImage_Item
import com.example.myapp.autoSlider.AutoProgram_Item
import com.example.myapp.autoSlider.AutoSlider_Adapter
import com.example.myapp.databinding.ActivityNormalSliderBinding
import com.example.myapp.databinding.ActivitySliderViewBinding
import com.example.myapp.normalSlider.ImageItem
import com.example.myapp.normalSlider.imagesNormal_Item
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class sliderView : AppCompatActivity() {
    // setting for class
    private var _binding : ActivitySliderViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private var programs: List<AutoProgram_Item> = mutableListOf()

    private var submitList2: MutableList<AutoImage_Item> = mutableListOf()
    // shared references
    private lateinit var sharedPreferences: SharedPreferences
    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = ActivitySliderViewBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        // set recycler view
        RVset()
        // dots
        indicatorDots()
    }

    private fun RVset(){
        val json = Json{ignoreUnknownKeys = true}
        val jsonString = assets.open("digitalmkt.json").bufferedReader().readText()

        val jsonElement: JsonElement = Json.parseToJsonElement(jsonString)
        programs = json.decodeFromString<List<AutoProgram_Item>>(jsonElement.toString())

        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", MODE_PRIVATE)

        val viewPager2 : ViewPager2 = findViewById(R.id.autoSliderget)
        // get data from shared references
        val storageID = sharedPreferences.getString("nodeIDEvent","")

        val imageAdapter = AutoSlider_Adapter(this)
        viewPager2.adapter = imageAdapter

        // create new list
        for (event in programs){
            val idEvent = event.eventID
            for (image in event.images){
                if (image.type == "1" && idEvent == storageID){
                    val newItem = AutoImage_Item(image.image_link, image.description, image.type)
                    submitList2.add(newItem)
                }
            }
        }
        imageAdapter.setData(submitList2)
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

    fun indicatorDots(){
        val slideDotLL = findViewById<LinearLayout>(R.id.slideDot)
        val dotsImage = Array(programs.size) { ImageView(this) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL.addView(it,params)
        }

        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index){
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    }else{
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        val viewPager2 : ViewPager2 = findViewById(R.id.autoSliderget)
        viewPager2.registerOnPageChangeCallback(pageChangeListener)
    }
}
