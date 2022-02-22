package com.sm.alarm.item

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.minors.alarm.R
import com.mobdeve.minors.alarm.item.Item
import com.sm.alarm.DBHelper
import java.lang.Exception
import java.nio.file.Files.size
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class Adapter(private val Item: ArrayList<Item>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    lateinit var DBHelper : DBHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.alarm_items, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = Item[position]
        holder.bindItems(Item[position])


        holder.apply {
            itemView.apply {
                val tvTime: TextView = findViewById(R.id.tvTime)
                val tvDays: TextView = findViewById(R.id.tvDays)
                val tvAMPM: TextView = findViewById(R.id.tvAMPM)
                val imgDelete: ImageView = findViewById(R.id.imgDelete)
                val switchDays: Switch = findViewById(R.id.switchDays)
                val currentState : Boolean = parent.isON == 1
                switchDays.isChecked = currentState

                tvTime.text = convertTo12Hours(parent.alarmTime + ":00")
                tvAMPM.text = AMPM(parent.alarmTime + ":00")
                tvDays.text = parent.days

                imgDelete.setOnClickListener {
                    DBHelper = DBHelper(context)
                    DBHelper.deleteAlarm(parent.id.toInt())

                    try {
                        Item.removeAt(position)
                        notifyItemRemoved(position)
                        notifyDataSetChanged()
                    } catch (e: Exception) {

                    }
                }

                switchDays.setOnCheckedChangeListener { _, isChecked  ->
                    var isOn = if (isChecked) 1 else 0
                    DBHelper = DBHelper(context)
                    DBHelper.setAlarm(parent.id.toInt(),isOn)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return Item.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: Item) {

        }
    }

    fun convertTo12Hours(militaryTime: String): String{
        val inputFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        val date = inputFormat.parse(militaryTime)
        return outputFormat.format(date)
    }

    fun AMPM(militaryTime: String): String{
        val inputFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("aa", Locale.getDefault())
        val date = inputFormat.parse(militaryTime)
        return outputFormat.format(date)
    }

}