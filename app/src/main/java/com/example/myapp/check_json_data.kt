package com.example.myapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject

class check_json_data : AppCompatActivity() {
    lateinit var textVew : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_check_json_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textVew = findViewById(R.id.textView2)
        val jsonData = applicationContext.resources.openRawResource(applicationContext.resources.getIdentifier(
            "digitalmkt", "raw",
            applicationContext.packageName)).bufferedReader().use { it.readText() }
        val outputJsonArray = JSONArray(jsonData) // Chuyển đổi chuỗi JSON thành mảng JSON

        for (i in 0 until outputJsonArray.length()) {
            val jsonObject = outputJsonArray.getJSONObject(i) // Lấy đối tượng JSON từ mảng

            val id = jsonObject.getInt("id")
            val name = jsonObject.getString("name")
            val description = jsonObject.getString("description")
            val imageLink = jsonObject.getString("image_link")
            val usageType = jsonObject.getString("usage_type")

            val previous = textVew.text
            val data: String = "$id \n $name \n $description \n $imageLink \n $usageType"
            textVew.text = previous.toString() + data

        }
    }
}