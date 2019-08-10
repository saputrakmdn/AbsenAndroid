package com.mauk.absen

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import java.util.*
import android.R
import android.support.v4.app.NotificationCompat
import android.app.PendingIntent
import com.mauk.absen.view.MainActivity







class TesService:Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("inputExtra")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, "exampleServiceChannel")
            .setContentTitle("Example Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_notification_overlay)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}