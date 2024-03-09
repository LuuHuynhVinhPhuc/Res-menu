package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.widget.Toast
import androidx.core.view.MotionEventCompat

class sliderView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_slider_view)
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
