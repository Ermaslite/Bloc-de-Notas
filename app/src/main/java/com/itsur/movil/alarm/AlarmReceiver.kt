package com.itsur.movil.alarm
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.itsur.movil.R


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "STOP_ALARM") {
            stopAlarm(context)
            return
        }

        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        val channelId = "alarm_channel"
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear un Intent para detener la alarma
        val stopIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = "STOP_ALARM"
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarma")
            .setContentText("Notificación: $message")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(alarmSound)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_launcher_foreground, "Detener", stopPendingIntent)
            .build()

        notificationManager.notify(1, notification)

        // Reproducir el sonido de alarma
        mediaPlayer = MediaPlayer.create(context, alarmSound)
        mediaPlayer?.start()
    }

    private fun stopAlarm(context: Context?) {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(1)
    }
}
