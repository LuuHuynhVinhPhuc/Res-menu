package com.example.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.JsonReader
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp.autoSlider.AutoProgram_Item
import com.example.myapp.databinding.ActivityNormalSliderBinding
import com.example.myapp.databinding.ActivityScheduleFoodsBinding
import com.example.myapp.jsonData.jsonData
import com.example.myapp.normalSlider.ImageItem
import com.example.myapp.normalSlider.imagesNormal_Item
import com.example.myapp.normalSlider.viewPager_Adapter
import com.example.myapp.recycleView.images_Item
import com.example.myapp.recycleView.program_Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import java.util.UUID

class normal_Slider : AppCompatActivity() {

    // setting for class
    private var _binding : ActivityNormalSliderBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewpager2 : ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private var imageList: List<ImageItem> = mutableListOf()

    private var submitList: MutableList<imagesNormal_Item> = mutableListOf()
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
        _binding = ActivityNormalSliderBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        viewpager2 = findViewById(R.id.view_pager2)
        // go to sign in with button
        val btnChangetoSign_in : AppCompatImageButton = findViewById(R.id.btnChangetoSignin)
        btnChangetoSign_in.setOnClickListener(){
            val i = Intent(this, sign_in::class.java)
            startActivity(i)
        }

        // load recycler view
        RVload()

        // Indicator Dots
        indicatorDots()
    }
    fun RVload(){
        val json = Json{ignoreUnknownKeys = true}
        val jsonString = assets.open("digitalmkt.json").bufferedReader().readText()

        val jsonElement: JsonElement = Json.parseToJsonElement(jsonString)
        imageList = json.decodeFromString<List<ImageItem>>(jsonElement.toString())

        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", MODE_PRIVATE)

        // get data from shared references
        val storageID = sharedPreferences.getString("nodeIDEvent","")

        val imageAdapter = viewPager_Adapter(this)
        viewpager2.adapter = imageAdapter

        // create new list
        for (event in imageList){
            val idEvent = event.eventID
            for (image in event.images){
                if (image.type == "0" && idEvent == storageID){
                    val newItem = imagesNormal_Item(image.image_link, image.description, image.type)
                    submitList.add(newItem)
                }
            }
        }
        imageAdapter.setData(submitList)
    }

    fun indicatorDots(){
        val slideDotLL = findViewById<LinearLayout>(R.id.slideDot)
        val dotsImage = Array(imageList.size) { ImageView(this)}

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
        viewpager2.registerOnPageChangeCallback(pageChangeListener)
    }

}

