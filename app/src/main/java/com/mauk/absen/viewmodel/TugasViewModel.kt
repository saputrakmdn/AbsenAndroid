package com.mauk.absen.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.mauk.absen.adapter.TugasAdapter
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Tugas
import com.mauk.absen.service.Api
import com.mauk.absen.service.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TugasViewModel(application: Application): AndroidViewModel(application) {

    var listTugas : MutableLiveData<List<Tugas>>? = null
    var retrofit : Retrofit? = null
    var set: SharePref? = null
    val dataTugas: LiveData<List<Tugas>>

    get() {
        if (listTugas == null){
            listTugas = MutableLiveData()
            loadData()
        }
        return listTugas!!
    }
    fun loadData(){
        set = SharePref(getApplication())
        var id = set!!.getKelas()
        retrofit = Retrofit.Builder().baseUrl(Const.base_url).addConverterFactory(GsonConverterFactory.create()).build()
        var tugas = retrofit!!.create(Api::class.java)
        tugas.tugas("${id}").enqueue(object : Callback<List<Tugas>> {
            override fun onFailure(call: Call<List<Tugas>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Tugas>>, response: Response<List<Tugas>>) {

                listTugas!!.value = response.body()

            }

        })
    }
}