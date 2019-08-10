package com.mauk.absen

import android.app.Application
import android.content.Intent
import android.support.multidex.MultiDexApplication
import android.support.v4.content.ContextCompat

class ktApplication:MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        ContextCompat.startForegroundService(this,Intent(this, Tes::class.java))
    }

}