package com.gotz.presentation.util

import android.util.Log

object GLog {
    private const val TAG = "GOTZ_TEST"

    fun messageLog(message: String){
        Log.e(TAG, message)
    }
}