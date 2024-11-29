package com.itsur.movil.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.itsur.movil.DataBase.NoteDatabase


class AlarmRebootReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val alarmScheduler = AlarmSchedulerImpl(context)
            val alarmDao = NoteDatabase.getDatabase(context).alarmDao()

            val alarms = alarmDao.getAllAlarms() // Recuperar todas las alarmas
            alarms.forEach { alarmItem ->
                alarmScheduler.schedule(alarmItem) // Reprogramar cada alarma
            }
        }
    }
}
