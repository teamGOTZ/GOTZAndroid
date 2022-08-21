package com.gotz.presentation.util

import android.util.Log
import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

//class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
//    override fun onChanged(event: Event<T>?) {
//        event?.getContentIfNotHandled()?.let {
//            onEventUnhandledContent(it)
//        }
//    }
//}

class EventObserver(private val eventContent: () -> Unit) : Observer<Event<Boolean>> {
    override fun onChanged(event: Event<Boolean>?) {
        Log.e("Event", "onChanged")
        event?.getContentIfNotHandled()?.let { isEvent ->
            if (isEvent) eventContent()
        }
    }
}