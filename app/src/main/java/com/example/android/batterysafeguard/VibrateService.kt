package com.example.android.batterysafeguard

import android.app.Service
import android.content.*
import android.os.BatteryManager
import android.os.IBinder
import android.util.Log

class VibrateService : Service() {

    private val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    private val vibrateReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("VibrateService", "Receiver is registered.")

            intent?.let {
                if (it.action == Intent.ACTION_BATTERY_CHANGED) {
                    val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    val percentage = level * 100 / scale

                    context?.let {
                        sendPercentage(percentage, context)
                        notifyUser(context, percentage)
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(vibrateReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    private fun sendPercentage(percentage: Int, context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(MainActivity.PERCENTAGE_KEY, percentage)
        context.startActivity(intent)
    }

    private fun notifyUser(context: Context, percentage: Int) {
        VibrateTask.notifyUser(VibrateTask.VIBRATE, percentage, context)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        unregisterReceiver(vibrateReceiver)
        super.onDestroy()
    }
}