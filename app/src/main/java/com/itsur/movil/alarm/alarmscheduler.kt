package com.itsur.movil.alarm


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarms")
data class AlarmItem(
    @PrimaryKey val alarmId: Int,
    val alarmTimestamp: Long,
    val message: String
)
