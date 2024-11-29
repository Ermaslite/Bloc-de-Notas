package com.itsur.movil.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itsur.movil.alarm.AlarmItem

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms")
    fun getAllAlarms(): List<AlarmItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarmItem: AlarmItem)

    @Query("DELETE FROM alarms WHERE alarmId = :alarmId")
    fun delete(alarmId: Int)
}
