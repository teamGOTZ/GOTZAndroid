package com.gotz.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

object Utils{
    fun startActivity(context: Context?, activity: Activity){
        context?.run {
            val intent = Intent(this, activity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(context: Context?, msg: String) {
        context?.run {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}