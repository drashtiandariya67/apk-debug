package com.example.callrecorder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        val whitelist = listOf("+919876543210", "1234567890")

        if (TelephonyManager.EXTRA_STATE_OFFHOOK == state && whitelist.contains(number)) {
            val serviceIntent = Intent(context, RecorderService::class.java)
            serviceIntent.putExtra("number", number)
            ContextCompat.startForegroundService(context, serviceIntent)
        } else if (TelephonyManager.EXTRA_STATE_IDLE == state) {
            context.stopService(Intent(context, RecorderService::class.java))
        }
    }
}
