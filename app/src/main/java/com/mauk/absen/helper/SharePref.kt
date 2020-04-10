package com.mauk.absen.helper

import android.content.Context
import android.content.SharedPreferences
import com.mauk.absen.viewmodel.TugasViewModel

class SharePref {
    internal var mContext : Context
    private var sharePref: SharedPreferences
    private var KELAS: String = "kelas"
    private var login: Boolean = false
    private var ABSEN: String = "absen"
    constructor(context: Context){
        mContext = context
        sharePref = mContext.getSharedPreferences("nis", Context.MODE_PRIVATE)
    }
    fun readSetting(key: String):String{
        return sharePref.getString(key, "na")
    }
    fun updateSetting(key: String, value: String){
        val editor = sharePref.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun setKelas(kelas: String){
        val editor = sharePref.edit()
        editor.putString(KELAS, kelas)
        editor.apply()
    }
    fun getKelas(): String{
        return sharePref.getString(KELAS, " ")
    }
    fun setAbsen(absen: String){
        val editor = sharePref.edit()
        editor.putString(ABSEN, absen)
        editor.apply()
    }
    fun getAbsen(): String{
        return sharePref.getString(ABSEN, " ")
    }
    fun isLogin(tes: Boolean){
        val editor = sharePref.edit()
        editor.putBoolean("login", tes)
        editor.apply()
    }
    fun statusLogin():Boolean{
        return sharePref.getBoolean("login", false)
    }
    fun isMasuk(masuk: Boolean){
        val editor = sharePref.edit()
        editor.putBoolean("masuk", masuk)
        editor.apply()
    }
    fun statusMasuk():Boolean{
        return sharePref.getBoolean("masuk", false)
    }
    fun deleteMasuk(){
        sharePref.edit().remove("masuk").commit()
    }
    fun isPulang(masuk: Boolean){
        val editor = sharePref.edit()
        editor.putBoolean("pulang", masuk)
        editor.apply()
    }
    fun statusPulang():Boolean{
        return sharePref.getBoolean("pulang", false)
    }
    fun deletePulang(){
        sharePref.edit().remove("pulang").commit()
    }
    fun deleteAllSetting(){
        sharePref.edit().remove("nis").commit()
    }
}