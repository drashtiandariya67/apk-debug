package com.example.callrecorder

import android.os.Build

object DeviceUtils {
    fun hasNativeCallRecording(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        return when {
            manufacturer.contains("samsung") -> true
            manufacturer.contains("xiaomi") -> true
            manufacturer.contains("oppo") -> true
            manufacturer.contains("realme") -> true
            else -> false
        }
    }
}
