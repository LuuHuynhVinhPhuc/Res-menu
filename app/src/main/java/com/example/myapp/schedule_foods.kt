package com.example.myapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapp.databinding.ActivityScheduleFoodsBinding
import com.example.myapp.loading_progress.loadingDialog
import com.example.myapp.normalSlider.ImageItem
import com.example.myapp.recycleView.images_Adapter
import com.example.myapp.recycleView.program_Item
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import kotlinx.serialization.json.JsonElement
import java.io.File

class schedule_foods : AppCompatActivity() {
    // setting for class
    private var _binding: ActivityScheduleFoodsBinding? = null
    private val binding get() = _binding!!

    // default variables
    private lateinit var recyclerView: ViewPager2
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
        btnCancel.setOnClickListener {
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }

//        // for download button
//        val downloadBtn = findViewById<AppCompatButton>(R.id.buttonDownload)
//        downloadBtn.setOnClickListener(){
//            val context = this
//            showAlert(context, "Đồng ý download dữ liệu", "Bạn có muốn download dữ liệu từ API đã cài đặt?"){
//                val loading = loadingDialog(context)
//                loading.startLoading()
//                Handler(Looper.getMainLooper()).postDelayed(object : Runnable{
//                    override fun run() {
//                        loading.isDismiss()
//                        val i = Intent(context, sliderView::class.java)
//                        startActivity(i)
//                        finish()
//                    }
//                },5000)
//            }
//        }


        val btnSave = findViewById<Button>(R.id.buttonSaveSchedule)
        btnSave.setOnClickListener {
            val idEvent = findViewById<TextView>(R.id.idEvent)

            val sharedPreferences = getSharedPreferences("Even_ID", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("nodeIDEvent", idEvent.text.toString())

            editor.apply()

            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }
        // read JSON file
        JsonReader()
        // set up recycler view
        RVset()
    }

    // read JSON files
    fun JsonReader() {
        // read json files
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = assets.open("digitalmkt.json").bufferedReader().readText()

        val jsonElement: JsonElement = Json.parseToJsonElement(jsonString)
        programs = json.decodeFromString<List<program_Item>>(jsonElement.toString())
    }

    // recycler view set up
    fun RVset() {
        recyclerView.adapter = adapter
        adapter.setData(programs)

        recyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL

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