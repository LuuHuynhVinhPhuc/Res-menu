package com.example.myapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp.autoSlider.AutoImage_Item
import com.example.myapp.autoSlider.AutoProgram_Item
import com.example.myapp.autoSlider.AutoSlider_Adapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Handler

class sliderView : AppCompatActivity() {

    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private var programs: List<AutoProgram_Item> = mutableListOf()

    private var submitList2: MutableList<AutoImage_Item> = mutableListOf()
    // shared references
    private lateinit var sharedPreferences: SharedPreferences

    private var viewPagerClicked = false
    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_slider_view)

        // set recycler view
        RVset()
        // dots
        indicatorDots()

        val viewPager = findViewById<ViewPager2>(R.id.autoSliderget)
        viewPager.isUserInputEnabled = false
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
        val storageID = sharedPreferences.getString("nodeIDEvent","1")

        // for time Event


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

                // auto slider
        val handler = android.os.Handler(Looper.getMainLooper())
        val runable = Runnable{
            // Lấy vị trí trang hiện tại
            val currentItem = viewPager2.currentItem
            // Tính vị trí của trang tiếp theo
            val nextItem = currentItem + 1
            // Kiểm tra nếu vị trí của trang tiếp theo vượt quá số lượng trang
            if (nextItem >= imageAdapter.itemCount) {
                // Nếu vượt quá, chuyển đến trang đầu tiên
                viewPager2.currentItem = 0
            } else {
                // Nếu không, chuyển đến trang tiếp theo
                viewPager2.currentItem = nextItem
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run(){
                handler.post(runable)
            }
        },4000,4000)
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
