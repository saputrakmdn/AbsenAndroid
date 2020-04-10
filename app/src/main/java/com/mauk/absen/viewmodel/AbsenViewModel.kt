package com.mauk.absen.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Absen
import com.mauk.absen.model.Tugas
import com.mauk.absen.service.Api
import com.mauk.absen.service.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AbsenViewModel(application: Application): AndroidViewModel(application) {
    var listAbsen : MutableLiveData<List<Absen>>? = null
    var retrofit : Retrofit? = null
    var set: SharePref? = null
    val dataTugas: LiveData<List<Absen>>
    get() {
        if(listAbsen == null){
            listAbsen = MutableLiveData()
            loadData()
        }
        return listAbsen!!
    }

    private fun loadData() {
        set = SharePref(getApplication())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
        var id = set!!.readSetting(Const.PREF_MY_ID)
        retrofit = Retrofit.Builder().client(client).baseUrl(Const.base_url).addConverterFactory(GsonConverterFactory.create()).build()
        var tugas = retrofit!!.create(Api::class.java)
        tugas.absen("${id}").enqueue(object : Callback<List<Absen>>{
            override fun onFailure(call: Call<List<Absen>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Absen>>, response: Response<List<Absen>>) {
                listAbsen!!.value = response.body()
            }

        })
    }
}