package com.example.myapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputLayout

class admin_setting : AppCompatActivity() {
    override fun onDestroy() {
        val handler = Handler(Looper.getMainLooper())
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_admin_setting)

        // edit event : go to slider auto page
        val btnCancel = findViewById<Button>(R.id.button2)
        btnCancel.setOnClickListener{
            val i = Intent(this,sliderView::class.java)
            startActivity(i)
        }
        val saveBtn = findViewById<Button>(R.id.buttonSave)
        saveBtn.setOnClickListener(){
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }

        // for Spinner ( auto complete text view )
        val items = listOf("Chi nhánh 1", "Chi nhánh 2", "Chi nhánh 3", "Chi nhánh 4")
        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto)

        val adapter = ArrayAdapter(this,R.layout.list_item, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val itemSelected = parent.getItemAtPosition(position)
            Toast.makeText(this, "Item : $itemSelected", Toast.LENGTH_SHORT).show()
        }

        // check exist data inside 2 edit text
        val apiLink : EditText = findViewById(R.id.btnAPI)
        val chooseStore : TextInputLayout = findViewById(R.id.chooseStore)

        val buttonSave : Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener(){
            if(apiLink.text.isEmpty() || chooseStore.isEmpty()){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Thiếu thông tin")
                builder.setMessage("Vui lòng kiểm tra lại bạn thiếu sót hoặc sai thông tin.")
                builder.setPositiveButton("OK", null)
                builder.show()
            }else{
                Toast.makeText(this, "Lưu thành công!", Toast.LENGTH_SHORT).show()
                // turn to sliderView
                val i = Intent(this, sliderView::class.java)
                startActivity(i)
            }
        }
    }

}