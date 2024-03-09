package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button

class choose_options : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_choose_options)

        val handler = Handler(Looper.getMainLooper())
        handler.removeCallbacksAndMessages(null)
        // sign in page
        // find cancel button
        val cancelBtn = findViewById<Button>(R.id.buttonCancel)
        // add an event for btn : turn back to sign in page
        cancelBtn.setOnClickListener {
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }
        // admin page
        // find button
        val adminBtn = findViewById<Button>(R.id.buttonsetting)
        // add event for btn : go to setting page
        adminBtn.setOnClickListener {
            val i = Intent(this, admin_setting::class.java)
            startActivity(i)
        }
        // optimize page
        // find button
        val optimizeBtn = findViewById<Button>(R.id.buttonOptimize)
        // add event for btn : go to optimize page
        optimizeBtn.setOnClickListener {
            val i = Intent(this, schedule_foods::class.java)
            startActivity(i)
        }
    }
}