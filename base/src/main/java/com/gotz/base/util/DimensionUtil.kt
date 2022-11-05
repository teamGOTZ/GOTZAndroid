package com.gotz.base.util

import android.content.Context
import android.util.DisplayMetrics

object DimensionUtil {

    fun dpToPx(context: Context, dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun dpToPx(context: Context, dp:Int): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}