package com.itsur.movil.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itsur.movil.alarm.AlarmItem


@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val mediaUris: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val reminders: List<AlarmItem> = emptyList()
)
