package com.itsur.movil.alarm

import com.itsur.movil.DataBase.AlarmDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlarmRepository(private val alarmDao: AlarmDao, private val alarmScheduler: AlarmScheduler) {

    suspend fun addAlarm(alarmItem: AlarmItem) {
        withContext(Dispatchers.IO) {
            alarmDao.insert(alarmItem)
            alarmScheduler.schedule(alarmItem)
        }
    }

    suspend fun removeAlarm(alarmItem: AlarmItem) {
        withContext(Dispatchers.IO) {
            alarmDao.delete(alarmItem.alarmId)
            alarmScheduler.cancel(alarmItem)
        }
    }

    suspend fun getAlarms(): List<AlarmItem> {
        return withContext(Dispatchers.IO) {
            alarmDao.getAllAlarms()
        }
    }
}
