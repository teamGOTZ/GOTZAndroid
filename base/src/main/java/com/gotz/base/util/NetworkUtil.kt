package com.gotz.base.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtil {

    /**
     * 기기의 현재 네트워크 상태를 확인 한다.
     */
    fun isNetworkEnable(context: Context?): Boolean {
        if(context == null) return false

        return try {
            val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true

                else -> false
            }

        } catch (e: java.lang.Exception) {
            false
        }
    }
}