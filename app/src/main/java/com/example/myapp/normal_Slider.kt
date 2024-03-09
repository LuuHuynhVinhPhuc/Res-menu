package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.appcompat.widget.AppCompatImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp.jsonData.jsonData
import com.example.myapp.normalSlider.ImageItem
import com.example.myapp.normalSlider.viewPager_Adapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.net.URL
import java.util.UUID

class normal_Slider : AppCompatActivity() {
    private val DELAY_TIME_MS: Long = 90000 // 60 seconds
    private lateinit var viewpager2 : ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }
    @SuppressLint("ClickableViewAccessibility", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_normal_slider)

        viewpager2 = findViewById(R.id.view_pager2)
        // go to sign in with button
        val btnChangetoSign_in : AppCompatImageButton = findViewById(R.id.btnChangetoSignin)
        btnChangetoSign_in.setOnClickListener(){
            val i = Intent(this, sign_in::class.java)
            startActivity(i)
        }
        // turn back to slider auto after 1 mins
        // auto change page after 20s
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
            finish()
        }, DELAY_TIME_MS)

        // listen for touch srceen
        findViewById<View>(android.R.id.content).setOnTouchListener { _, _ ->
            // Hủy định thời
            handler.removeCallbacksAndMessages(null)
            false // Trả về false để tiếp tục lắng nghe sự kiện chạm
        }

//        val inputStream: InputStream = resources.openRawResource(R.raw.digitalmkt)
//        val jsonString = inputStream.bufferedReader().use { it.readText() }
//        val menuItems = Gson().fromJson<List<jsonData>>(jsonString, object : TypeToken<List<jsonData>>(){}.type)
//
//        var counter = 0
////        // menuItems là danh sách các đối tượng MenuItem bạn đã parse từ JSON
////        // Đảm bảo rằng menuItems là danh sách thích hợp được parse từ JSON trước đó
//        val imageMoveList = mutableListOf<ImageItem>()
//        val imageList = arrayListOf<ImageItem>()
//        // create a multi list to
//        for (item in menuItems) {
//            val drawableId = resources.getIdentifier(item.image_link, "drawable", packageName)
//            // Tạo một đối tượng ImageItem mới với các thông tin từ JSON
//            val imageItem = ImageItem(
//                item.id,
//                "android.resource://$packageName/$drawableId" // Sử dụng đường dẫn hình ảnh từ JSON
//            )
//            counter++
//            if (counter == 3) {
//                break
//            }
//            imageMoveList.add(imageItem)
//        }
        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.main
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.menu1
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.menu2
            )
        )


        val imageAdapter = viewPager_Adapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        // Indicator Dots

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
    override fun onDestroy() {
        super.onDestroy()
        viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
    }
}