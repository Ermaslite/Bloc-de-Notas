package com.itsur.movil.Models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itsur.movil.alarm.AlarmItem


class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromAlarmItemList(value: List<AlarmItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toAlarmItemList(value: String): List<AlarmItem> {
        val listType = object : TypeToken<List<AlarmItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromLong(value: Long): String {
        return value.toString()
    }

    @TypeConverter
    fun toLong(value: String): Long {
        return value.toLong()
    }
}
