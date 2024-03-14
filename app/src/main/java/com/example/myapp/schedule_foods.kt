package com.example.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.Preference
import android.preference.PreferenceDataStore
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.datastore.core.DataStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ActivityScheduleFoodsBinding
import com.example.myapp.loading_progress.loadingDialog
import com.example.myapp.recycleView.images_Adapter
import com.example.myapp.recycleView.images_Item
import com.example.myapp.recycleView.program_Item
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import retrofit2.Retrofit
import java.util.ArrayList

class schedule_foods : AppCompatActivity() {
    // setting for class
    private var _binding : ActivityScheduleFoodsBinding? = null
    private val binding get() = _binding!!

    // default variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: images_Adapter
    private var programs: List<program_Item> = listOf()

    // shared references
    private lateinit var sharedPreferences: SharedPreferences
    override fun onDestroy() {
        val handler = Handler(Looper.getMainLooper())
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = ActivityScheduleFoodsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        // container
        recyclerView = findViewById(R.id.rvImage_and_Video)
        adapter = images_Adapter(this)

        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", Context.MODE_PRIVATE)

        // edit for cancel : go to choosing page
        val btnCancel = findViewById<Button>(R.id.buttonSkip)
        btnCancel.setOnClickListener{
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
        // save and go to slider
        val btnSave = findViewById<Button>(R.id.buttonSaveSchedule)
        btnSave.setOnClickListener(){

            // get ID item
            val id : TextView = findViewById(R.id.idEvent)
            val eventID = id.text.toString()

            // create share references to storage
            val sharedEdit = sharedPreferences.edit()
            sharedEdit.putString("nodeIDEvent", eventID)
            sharedEdit.apply()

            // Toast for saved successful
            Toast.makeText(this, "Storage successful", Toast.LENGTH_SHORT).show()

            // Intent
            val i = Intent(this, normal_Slider::class.java)
            startActivity(i)
        }

        // read JSON file
        JsonReader()
        // set up recycler view
        RVset()
    }

    // read JSON files
    fun JsonReader(){
        // read json files
        val json = Json{ignoreUnknownKeys = true}
        val jsonString = assets.open("digitalmkt.json").bufferedReader().readText()

        val jsonElement: JsonElement = Json.parseToJsonElement(jsonString)
        programs = json.decodeFromString<List<program_Item>>(jsonElement.toString())
    }

    // recycler view set up
    fun RVset(){
        recyclerView.adapter = adapter
        adapter.setData(programs)


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        // snapHelper for focus elements
        adapter.attachSnapHelper(recyclerView)
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