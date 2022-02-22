package com.mobdeve.minors.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.minors.alarm.item.Item
import com.sm.alarm.DBHelper
import com.sm.alarm.item.Adapter

class MainActivity : AppCompatActivity() {
    lateinit var DBHelper : DBHelper
    private var rvAlarm: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBHelper = DBHelper(this)
        DBHelper.createTable()

        val imgAdd: ImageView = findViewById(R.id.imgAdd)
        val tvTitle: TextView = findViewById(R.id.tvTitle)
        rvAlarm = findViewById(R.id.rvAlarm)

        imgAdd.setOnClickListener {
            val intent = Intent(this, AddTime::class.java)
            startActivity(intent)
        }

        getTime()
    }

    private fun getTime() {
        var items = DBHelper.getTime()
        val postItems = ArrayList<Item>()

        items.forEach {
            postItems.add(
                Item(
                    it.id,
                    it.alarmTime,
                    it.days,
                    it.isON
                )
            )
        }

        val objAdapter = Adapter(postItems)


        rvAlarm?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvAlarm?.adapter = objAdapter
    }

    override fun onResume() {
        super.onResume()

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                val tvTitle: TextView = findViewById(R.id.tvTitle)
                tvTitle.text = DBHelper.countAlarmOn()
                mainHandler.postDelayed(this, 1000)

                if (rvAlarm?.size == 0) {
                    tvTitle.text = "No alarm set"
                }
            }
        })

        getTime()
    }
}