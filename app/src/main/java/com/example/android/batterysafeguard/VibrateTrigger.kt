package com.example.android.batterysafeguard

import android.content.Context
import android.content.SharedPreferences

class VibrateTrigger(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(VIBRATE_PREFERENCE, Context.MODE_PRIVATE)

    var batteryGuardValue: Int
        set(value) {
            sharedPreferences.edit().putInt(VIBRATE_KEY, value).apply()
        }
        get() = sharedPreferences.getInt(VIBRATE_KEY, 93)

    companion object {

        const val VIBRATE_PREFERENCE = "My preference"
        const val VIBRATE_KEY = "vibrate"
    }
}