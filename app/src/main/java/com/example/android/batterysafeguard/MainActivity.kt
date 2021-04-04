package com.example.android.batterysafeguard

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var vibrateTrigger: VibrateTrigger

    private val guardWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            editable?.let {
                updateBatteryGuardValue(it)
            }
        }
    }

    private fun updateBatteryGuardValue(editable: Editable) {
        try {
            vibrateTrigger.batteryGuardValue =
                if (editable.isNotEmpty()) editable.toString().toInt() else 93

        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrateTrigger = VibrateTrigger(this)
        startVibrateService()
    }

    override fun onStart() {
        super.onStart()
        intent?.let {
            val percentage = it.getIntExtra(PERCENTAGE_KEY, 0)
            populateUI(percentage)
        }
    }

    private fun startVibrateService() {
        val intent = Intent(this, VibrateService::class.java)
        startService(intent)
    }

    private fun populateUI(percentage: Int) {
        tvBattery.text = getString(R.string.txt_battery_with_percentage, percentage)
        progressBar.progress = percentage
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setValue) {
            letUserSelectBatteryGuardValue()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun letUserSelectBatteryGuardValue() {
        MaterialDialog(this).show {
            setContentView(R.layout.battery_guard)

            val value = findViewById<AppCompatEditText>(R.id.etButteryGuard)
            value.setText(vibrateTrigger.batteryGuardValue.toString())

            val accept = findViewById<AppCompatTextView>(R.id.tvAccept)
            accept.setOnClickListener {
                dismiss()
            }

            value.addTextChangedListener(guardWatcher)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, VibrateService::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    companion object {
        const val PERCENTAGE_KEY = "percentage"
    }
}