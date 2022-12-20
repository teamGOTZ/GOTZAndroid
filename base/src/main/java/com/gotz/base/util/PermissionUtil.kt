package com.gotz.base.util

import android.content.Context
import android.content.pm.PackageManager

object PermissionUtil {

    fun Context.isPermissionGranted(vararg permissions: String): Boolean {
        permissions.map { permission ->
            if(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) return true
        }
        return false
    }
}