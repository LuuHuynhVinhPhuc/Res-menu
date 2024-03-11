package com.example.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.loading_progress.loadingDialog
import com.example.myapp.recycleView.images_Adapter
import com.example.myapp.recycleView.images_Item
import com.example.myapp.recycleView.program_Item
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.JsonDecoder
import retrofit2.Retrofit
import java.util.ArrayList

class schedule_foods : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: images_Adapter
    private var programs: List<program_Item> = listOf()

    override fun onDestroy() {
        val handler = Handler(Looper.getMainLooper())
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_schedule_foods)

        recyclerView = findViewById(R.id.rvImage_and_Video)
        adapter = images_Adapter(this)

        // edit for cancel : go to choosing page
        val btnCancel = findViewById<Button>(R.id.buttonSkip)
        btnCancel.setOnClickListener{
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }
        // save and go to slider
        val btnSave = findViewById<Button>(R.id.buttonSave)
        btnSave.setOnClickListener(){
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }

        // for download button
        val downloadBtn = findViewById<AppCompatButton>(R.id.buttonDownload)
        downloadBtn.setOnClickListener(){
            val context = this
            showAlert(context, "Đồng ý download dữ liệu", "Bạn có muốn download dữ liệu từ API đã cài đặt?"){
                val loading = loadingDialog(context)
                loading.startLoading()
                Handler(Looper.getMainLooper()).postDelayed(object : Runnable{
                    override fun run() {
                        loading.isDismiss()
                        val i = Intent(context, sliderView::class.java)
                        startActivity(i)
                        finish()
                    }
                },5000)
            }
        }
        // for recyclerView

        // read json files
        val jsonString = assets.open("digitalmkt.json").bufferedReader().readText()
        val programs: List<program_Item> = Json.decodeFromString<List<program_Item>>(jsonString)

        recyclerView.adapter = adapter
        adapter.setData(programs)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

    }

    // show an alert when incorrect password
    fun showAlert(context: Context, title: String, message: String, onOkClicked: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            onOkClicked()
            // Dismiss the dialog when OK button is clicked
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}