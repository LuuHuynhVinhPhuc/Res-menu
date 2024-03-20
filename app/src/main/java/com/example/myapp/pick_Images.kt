package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pick_Images : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pick_images)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setup_spinner_type()
        setup_spinner_nameOfEvent()

        // cancel and turn back
        val cancelButton : AppCompatButton = findViewById(R.id.cancelBtnPicking)
        cancelButton.setOnClickListener{
            val i = Intent(this, schedule_foods::class.java)
            startActivity(i)
        }
    }

    private fun setup_spinner_nameOfEvent() {
        // create list
        val list = resources.getStringArray(R.array.event)
        // create adapter
        val adt = ArrayAdapter(this, R.layout.spinner_layout, list)
        // call spinner
        val spinnerType : Spinner = findViewById(R.id.eventItem)
        spinnerType.adapter = adt
    }

    private fun setup_spinner_type() {
        // create list
        val list = resources.getStringArray(R.array.type)
        // create adapter
        val adt = ArrayAdapter(this, R.layout.spinner_layout, list)
        // call spinner
        val spinnerType : Spinner = findViewById(R.id.typePage)
        spinnerType.adapter = adt
    }


}