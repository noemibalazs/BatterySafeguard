package com.example.android.batterysafeguard

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Vibrator

object VibrateTask {

    fun notifyUser(action: String, level: Int, context: Context) {

        val guardValue = VibrateTrigger(context).batteryGuardValue

        if (action == VIBRATE && level == guardValue) {
            val player = MediaPlayer.create(context, R.raw.tell_me_who)
            player.start()

            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2700)
        }
    }

    const val VIBRATE = "Vibrate"
}