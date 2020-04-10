package com.mauk.absen.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.LinearLayout
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Piket
import com.mauk.absen.model.Siswa
import com.mauk.absen.service.Api
import com.mauk.absen.service.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    var listPiket : MutableLiveData<Siswa>? = null
    var retrofit : Retrofit? = null
    var set: SharePref? = null
    val dataPiket : LiveData<Siswa>

    get() {
        if (listPiket == null){
            listPiket = MutableLiveData()
            loadData()
        }
        return listPiket!!
    }

    private fun loadData() {
        set = SharePref(getApplication())
        val id = set!!.readSetting(Const.PREF_MY_ID)
        retrofit = Retrofit.Builder().baseUrl(Const.base_url).addConverterFactory(GsonConverterFactory.create()).build()
        var tugas = retrofit!!.create(Api::class.java)
        tugas.piket("${id}").enqueue(object : Callback<Siswa>{
            override fun onFailure(call: Call<Siswa>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Siswa>, response: Response<Siswa>) {
                listPiket!!.value = response.body()
            }

        })
    }

}