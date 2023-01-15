package com.gotz.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import com.gotz.presentation.R

class ScheduleChangeReceiver: BroadcastReceiver() {
    companion object {
        const val ACTION_SCHEDULE_ADD = "com.gotz.action.ACTION_SCHEDULE_ADD"
        const val ACTION_SCHEDULE_DELETE = "com.gotz.action.ACTION_SCHEDULE_DELETE"
        const val ACTION_SCHEDULE_UPDATE = "com.gotz.action.ACTION_SCHEDULE_UPDATE"
    }

    val filter : IntentFilter
        get() {
            return IntentFilter().apply {
                addAction(ACTION_SCHEDULE_ADD)
                addAction(ACTION_SCHEDULE_DELETE)
                addAction(ACTION_SCHEDULE_UPDATE)
            }
        }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_SCHEDULE_ADD -> {
                Toast.makeText(context, context.getString(R.string.schedule_added_kr), Toast.LENGTH_LONG).show()
            }
            ACTION_SCHEDULE_DELETE -> {
                Toast.makeText(context, context.getString(R.string.schedule_deleted_kr), Toast.LENGTH_LONG).show()
            }
            ACTION_SCHEDULE_UPDATE -> {
                Toast.makeText(context, context.getString(R.string.schedule_updated_kr), Toast.LENGTH_LONG).show()
            }
        }
    }
}