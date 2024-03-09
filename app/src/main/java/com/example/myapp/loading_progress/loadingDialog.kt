package com.example.myapp.loading_progress

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.example.myapp.R

class loadingDialog(val mActivity : Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading(){
        //setView
        val inFlater = mActivity.layoutInflater
        val dialogView = inFlater.inflate(R.layout.loading_item,null)
        // set Dialog
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)

        isdialog= builder.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}