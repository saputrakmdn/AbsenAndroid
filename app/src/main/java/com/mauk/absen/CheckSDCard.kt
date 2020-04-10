package com.mauk.absen

import android.os.Environment




class CheckSDCard {
    fun isSDCardPresent(): Boolean {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED
            )
        ) {
            return true
        }
        return false
    }
}