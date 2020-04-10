package com.mauk.absen.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log.e
import com.mauk.absen.R
import com.mauk.absen.viewmodel.AbsenViewModel
import kotlinx.android.synthetic.main.activity_absen.*
import java.util.*

class AbsenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen)
        val bulan = android.text.format.DateFormat.format("MMMM", Date())
        month.text = bulan
        val viewmodel = ViewModelProviders.of(this).get(AbsenViewModel::class.java)
        viewmodel.dataTugas.observeForever {
            for(data in it!!){
                nama.text = "Nama = "+data.siswa.nama
                kelas.text = "Kelas = "+data.kelas.nama_kelas
                break
            }
            val num = it!!.groupingBy { it.keterangan == "alfa" }.eachCount()
            val c = num.get(true).toString()
            e("tag", "$c")
            if (c == "null"){
                ket.text = "Sangat Baik"
            }else if(c == "1" ){
                ket.text = "Baik"
            }else if(c == "2" ){
                ket.text = "Cukup Baik"
            }else if(c == "3"){
                ket.text = "Buruk"
            }else if(c > "4"){
                ket.text = "Sangat Buruk"
            }
            alfa.text = c
            val izin_ = it!!.groupingBy { it.keterangan == "izin" }.eachCount()
            val b = izin_.get(true)
            izin.text = b.toString()
            val _sakit = it!!.groupingBy { it.keterangan == "sakit" }.eachCount()
            sakit.text = _sakit.get(true).toString()

            e("tag", "$c")
            detail.setOnClickListener {
                startActivity(Intent(this, DetailAbsenActivity::class.java))
            }


        }
    }
}