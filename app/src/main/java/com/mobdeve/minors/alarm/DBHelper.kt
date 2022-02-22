package com.sm.alarm

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.mobdeve.minors.alarm.item.Item
import java.lang.Exception

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    @Override
    override fun onCreate(p0: SQLiteDatabase?) {

    }

    @Override
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

    }

    fun createTable() {
        val db = writableDatabase
        db.execSQL("CREATE TABLE IF NOT EXISTS tblAlarm (id INTEGER PRIMARY KEY,alarmTime TIME,days VARCHAR(255),isOn INTEGER DEFAULT 0)")
    }

    fun addAlarm(time: String,days: String){
        val db = writableDatabase
        db.execSQL("INSERT INTO tblAlarm (alarmTime,days) VALUES ('$time','$days')")
    }

    fun deleteAlarm(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM tblAlarm WHERE id = $id")
    }

    fun setAlarm(id: Int,isOn : Int){
        val db = writableDatabase
        db.execSQL("UPDATE tblAlarm set isOn = $isOn WHERE id = $id")
    }

    @SuppressLint("Range")
    fun countAlarmOn () : String {
        var qty = 0
        val db = writableDatabase
        var cursor: Cursor? = null
        var message = "All alarms are off"

        cursor = db.rawQuery("SELECT * FROM tblAlarm WHERE isOn = 1", null)

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                qty++
                cursor.moveToNext()
            }
        }

        if (qty != 0) {
            if (qty == 1) {
                message = "1 alarm is on"
            } else {
                message = "$qty alarms are on"
            }
        }

        return message
    }

    @SuppressLint("Range")
    fun getTime(): ArrayList<Item> {
        val items = ArrayList<Item>()
        val db = writableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from tblAlarm", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        var id: String
        var alarmTime: String
        var days: String
        var isOn: Int

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getString(cursor.getColumnIndex("id"))
                alarmTime = cursor.getString(cursor.getColumnIndex("alarmTime"))
                days = cursor.getString(cursor.getColumnIndex("days"))
                isOn = cursor.getInt(cursor.getColumnIndex("isOn"))

                items.add(Item(id,alarmTime,days,isOn))
                cursor.moveToNext()
            }
        }
        return items
    }

    companion object{
        private val DATABASE_NAME = "alarm"
        private val DATABASE_VERSION = 1
    }

}