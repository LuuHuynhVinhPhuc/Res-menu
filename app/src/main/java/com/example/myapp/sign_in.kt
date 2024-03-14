package com.example.myapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewpager.widget.ViewPager
import kotlin.concurrent.timer

class sign_in : AppCompatActivity() {
    private val DELAY_TIME_MS: Long = 90000 // 1mins 30 seconds
    lateinit var runnable: Runnable
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_in)



        runnable = Runnable {
            val i = Intent(this, normal_Slider::class.java)
            startActivity(i)
            finish()
        }

        // auto change page after 1 mins 30s
        handler.postDelayed(runnable, DELAY_TIME_MS)
        // default password
        val passName = "phucluu"

        // set counting for sign in 3 times error
        var countingsign_in = 3
        // event for click btn
        val btnAcept = findViewById<Button>(R.id.buttonAcp)
        val editTxt: EditText = findViewById(R.id.editPassword)


        // set event
        btnAcept.setOnClickListener(){
            // Find EditText (redundant in this case)
            val passwordData: String = editTxt.text.toString().trim()
            if (passName == passwordData) {
                val intent = Intent(this, choose_options::class.java)
                startActivity(intent)
                //handler.removeCallbacks(runnable)
                finish()
            } else {
                countingsign_in -= 1
                if (countingsign_in > 0){
                    showAlert(this, "Vui lòng kiểm tra lại mã quản lý của bạn", "Bạn còn $countingsign_in lần đăng nhập") {
                        editTxt.setText("")
                    }
                }
                if (countingsign_in == 0){
                    showAlert(this, "Sai mã đăng nhập", "Hệ thống sẽ tự chuyển ra trang chủ trong 1 phút nữa") {
                        countingsign_in = 3

                        handler.postDelayed(runnable, DELAY_TIME_MS)
                    }
                }
            }
        }

        // for enter keyboard
        editTxt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val passwordData: String = editTxt.text.toString().trim()
                if (passName == passwordData) {
                    val intent = Intent(this, choose_options::class.java)
                    startActivity(intent)
                    //handler.removeCallbacksAndMessages(null)
                    finish()
                } else {
                    countingsign_in -= 1
                    if (countingsign_in > 0){
                        showAlert(this, "Vui lòng kiểm tra lại mã quản lý của bạn", "Bạn còn $countingsign_in lần đăng nhập") {
                            editTxt.setText("")
                        }
                    }
                    if (countingsign_in == 0){
                        showAlert(this, "Sai mã đăng nhập", "Hệ thống sẽ tự chuyển ra trang chủ trong 1 phút nữa") {
                            countingsign_in =3

                            handler.postDelayed(runnable, DELAY_TIME_MS)
                        }
                    }
                }
            }
            false
        }

        val cancelBtn = findViewById<Button>(R.id.buttonCancel)
        cancelBtn.setOnClickListener {
            val i = Intent(this, normal_Slider::class.java)
            startActivity(i)

            handler.removeCallbacksAndMessages(null)
        }
        // for button below
        val cancelBtn2 = findViewById<Button>(R.id.button_Cancel)
        cancelBtn2.setOnClickListener {
            val i = Intent(this, normal_Slider::class.java)
            startActivity(i)

            handler.removeCallbacksAndMessages(null)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
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
