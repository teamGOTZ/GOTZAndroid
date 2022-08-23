package com.gotz.presentation.util

import android.util.Log

object GotzTest {
    private val TAG = "GOTZ"

    fun logE(message: String){
        Log.e(TAG, message)
    }

    fun logD(message: String){
        Log.d(TAG, message)
    }

    fun logI(message: String){
        Log.i(TAG, message)
    }

    fun logW(message: String){
        Log.w(TAG, message)
    }

    fun logV(message: String){
        Log.v(TAG, message)
    }
}