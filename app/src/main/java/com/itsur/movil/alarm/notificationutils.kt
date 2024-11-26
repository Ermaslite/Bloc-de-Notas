package com.itsur.movil.alarm
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Alarm Notification"
        val descriptionText = "Channel for Alarm notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("alarm_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

