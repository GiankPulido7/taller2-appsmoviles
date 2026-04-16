package com.example.galvisjaddys

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        // 🔥 DEBUG (para saber si entra)
        Toast.makeText(context, "Receiver activado 🔔", Toast.LENGTH_LONG).show()

        val title = intent.getStringExtra("title") ?: "Tarea"
        val description = intent.getStringExtra("description") ?: ""

        val channelId = "task_channel"

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 🔥 Crear canal SOLO si es Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recordatorios",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify((System.currentTimeMillis() % 10000).toInt(), notification)
    }
}