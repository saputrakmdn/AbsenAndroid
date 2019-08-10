package com.mauk.absen

import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.mauk.absen.view.MainActivity

class Tes : Service() {
    private val NOTIF_ID = 1
    private val NOTIF_CHANNEL_ID = "Channel_Id"
    override fun onCreate() {
        super.onCreate()
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )

        startForeground(
            NOTIF_ID, NotificationCompat.Builder(
                this,
                NOTIF_CHANNEL_ID
            ) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setContentTitle("tes")
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build()
        )
    }
}
