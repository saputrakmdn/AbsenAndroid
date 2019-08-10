package com.mauk.absen.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.util.Log.*
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.mauk.absen.FinishActivity
import com.mauk.absen.R
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Absen
import com.mauk.absen.service.Api
import com.mauk.absen.service.Const
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit




class MainActivity : AppCompatActivity() {

    var scannedResult= ""
    var retrofit: Retrofit? = null
    lateinit var set: SharePref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        set = SharePref(this)
        var time = Calendar.getInstance().time.hours
        i("tag", "$time")

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder().client(client).baseUrl(Const.base_url).addConverterFactory(GsonConverterFactory.create()).build()

        btnScan.setOnClickListener {
            run{
                IntentIntegrator(this@MainActivity).setRequestCode(25).setOrientationLocked(true).initiateScan()
            }
        }
        when(time){
            21, 22, 23 -> btnScan.visibility = View.GONE
            20-> pulang.visibility = View.GONE
        }
        pulang.setOnClickListener {
            run{
                IntentIntegrator(this@MainActivity).setRequestCode(20).setOrientationLocked(true).initiateScan()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(resultCode, data)
        e("re", "e"+requestCode.toString())

        if(result != null){
            if (requestCode == 20){
                if(result!!.contents != null){
                    scannedResult = result.contents
                    val scan = result.contents.toString()
                    i("tes", "a"+result.contents)
                    val id = set.getAbsen()
                    w("tag", "WOY$id")
                    txtValue.text = "1111"
                    val absenpulang = retrofit!!.create(Api::class.java)
                    absenpulang.pulang("${id}").enqueue(object : Callback<Absen>{
                        override fun onFailure(call: Call<Absen>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(call: Call<Absen>, response: Response<Absen>) {
                            Toast.makeText(this@MainActivity, "Finis", Toast.LENGTH_SHORT).show()
                        }

                    })


                } else {
                    txtValue.text = "scan failed"
                }
            }else if(requestCode == 25){
                if(result.contents != null){
                    scannedResult = result.contents
                    val scan = result.contents.toString()
                    i("tes", "a"+result.contents)
                    val id = set.readSetting(Const.PREF_MY_ID)
                    val kelas = set.getKelas()
                    txtValue.text = scannedResult
                    val absenMasuk = retrofit!!.create(Api::class.java)
                    absenMasuk.masuk("${scan}", "${id}", "${kelas}").enqueue(object : Callback<Absen>{
                        override fun onFailure(call: Call<Absen>, t: Throwable) {
                            i("error", "error"+t.message)
                        }

                        override fun onResponse(call: Call<Absen>, response: Response<Absen>) {
                            i("id", "ini id"+response.body()!!.id)
                            val id = response.body()!!.id
                            set.setAbsen(id)
                        }

                    })

                } else {
                    txtValue.text = "scan failed"
                }
            }


        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {

        outState?.putString("scannedResult", scannedResult)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            scannedResult = it.getString("scannedResult")
            txtValue.text = scannedResult
        }
    }

    override fun onStart() {
        super.onStart()
        var time = Calendar.getInstance().time.hours
        when(time){
            21, 22, 23 -> btnScan.visibility = View.GONE
            20-> pulang.visibility = View.GONE
        }
    }
}
