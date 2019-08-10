package com.mauk.absen.view

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log.i
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.mauk.absen.R
import com.mauk.absen.Tes
import com.mauk.absen.TesService
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Siswa
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

class LoginActivity: AppCompatActivity() {
    private var etNis : EditText? = null
    private var btn : Button? = null
    var retrofit: Retrofit? = null
    lateinit var set: SharePref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        etNis = findViewById(R.id.inputNik)
        btn = findViewById(R.id.btnMasuk)
        set = SharePref(this)
        val st = set.statusLogin()

        if (st == true){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder().client(client).baseUrl(Const.base_url).addConverterFactory(GsonConverterFactory.create()).build()
        val postlogin = retrofit!!.create(Api::class.java)
        btn!!.setOnClickListener {
            val login = etNis!!.text.toString()
            postlogin.login("${login}").enqueue(object : Callback<Siswa>{
                override fun onFailure(call: Call<Siswa>, t: Throwable) {

                }

                override fun onResponse(call: Call<Siswa>, response: Response<Siswa>) {
                    i("tag","tag"+response.body()!!.nis.toString())
                    val nis = response.body()!!.id
                    val id_kelas = response.body()!!.kelas_id
                    set.updateSetting(Const.PREF_MY_ID, nis)
                    set.setKelas(id_kelas)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    set.isLogin(true)

                }

            })
        }


    }

}