package com.mauk.absen.view

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log.i
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.mauk.absen.R
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Siswa
import com.mauk.absen.service.Api
import com.mauk.absen.service.Const
import kotlinx.android.synthetic.main.login_activity.*
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
    private val LOCATION_PERMISSION = 1
    lateinit var set: SharePref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                LOCATION_PERMISSION
            )
        }
            etNis = findViewById(R.id.inputNik)
        btn = findViewById(R.id.btnMasuk)
        set = SharePref(this)
        val st = set.statusLogin()

        if (st == true){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
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
            val llPadding = 30
            val ll = LinearLayout(this)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam

            val progressBar = ProgressBar(this)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, llPadding, 0)
            progressBar.layoutParams = llParam

            llParam = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(this)
            tvText.text = "Loading ..."
            tvText.setTextColor(resources.getColor(R.color.background))
            tvText.textSize = 20f
            tvText.layoutParams = llParam

            ll.addView(progressBar)
            ll.addView(tvText)

            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setView(ll)

            val dialog = builder.create()
            dialog.show()
            val window = dialog.window
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window!!.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog.window!!.attributes = layoutParams
            }
            val login = etNis!!.text.toString()
            postlogin.login("${login}").enqueue(object : Callback<Siswa>{
                override fun onFailure(call: Call<Siswa>, t: Throwable) {
                    text_input_layout.error = "NIS TIDAK TERDAFTAR"
                    dialog.dismiss()
                }

                override fun onResponse(call: Call<Siswa>, response: Response<Siswa>) {
                    i("tag","tag"+response.body()!!.nis.toString())
                    val nis = response.body()!!.id
                    val id_kelas = response.body()!!.kelas_id
                    set.updateSetting(Const.PREF_MY_ID, nis)
                    set.setKelas(id_kelas)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    set.isLogin(true)
                    finish()

                }

            })
        }


    }
    fun setProgressDialog() {

        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = "Loading ..."
        tvText.setTextColor(resources.getColor(R.color.background))
        tvText.textSize = 20f
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        dialog.show()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}