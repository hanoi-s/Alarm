package com.mobdeve.minors.alarm

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sm.alarm.DBHelper
import java.util.*
import java.util.Calendar.HOUR_OF_DAY


@Suppress("DEPRECATION")
class AddTime : AppCompatActivity() {
    lateinit var DBHelper : DBHelper

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_time)

        DBHelper = DBHelper(this)

        val tpTime: TimePicker = findViewById(R.id.tpTime)
        val btnSetAlarm: Button = findViewById(R.id.btnSetAlarm)
        val chkSunday: CheckBox = findViewById(R.id.chkSunday)
        val chkMonday: CheckBox = findViewById(R.id.chkMonday)
        val chkTuesday: CheckBox = findViewById(R.id.chkTuesday)
        val chkWednesday: CheckBox = findViewById(R.id.chkWednesday)
        val chkThursday: CheckBox = findViewById(R.id.chkThursday)
        val chkFriday: CheckBox = findViewById(R.id.chkFriday)
        val chkSaturday: CheckBox = findViewById(R.id.chkSaturday)
        var selectedTime : String

        tpTime.apply {
            selectedTime = "$currentHour:$currentMinute"
        }

        tpTime.setOnTimeChangedListener() { _: TimePicker, _: Int, _: Int ->
            tpTime.apply {
                selectedTime = "$currentHour:$currentMinute"
            }
        }

        btnSetAlarm.setOnClickListener {
            val days: ArrayList<String> = ArrayList()

            if (chkSunday.isChecked) {
                days.add('S'.toString())
            }

            if (chkMonday.isChecked) {
                days.add('M'.toString())
            }

            if (chkTuesday.isChecked) {
                days.add('T'.toString())
            }

            if (chkWednesday.isChecked) {
                days.add('W'.toString())
            }

            if (chkThursday.isChecked) {
                days.add('H'.toString())
            }

            if (chkFriday.isChecked) {
                days.add('F'.toString())
            }

            if (chkSaturday.isChecked) {
                days.add('S'.toString())
            }

            Log.e("Time",selectedTime)
            Log.e("Days",days.joinToString(" "))

            DBHelper.addAlarm(selectedTime,days.joinToString(" "))
            finish()
        }


    }
}