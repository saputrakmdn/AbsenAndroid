package com.mauk.absen.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mauk.absen.R
import com.mauk.absen.adapter.TugasAdapter
import com.mauk.absen.helper.SharePref
import com.mauk.absen.model.Tugas
import com.mauk.absen.viewmodel.TugasViewModel
import retrofit2.Retrofit

class TugasActivity: AppCompatActivity() {
    var retrofit: Retrofit? = null
    lateinit var set: SharePref
    var rcView : RecyclerView? = null
    var tugasAdapter: TugasAdapter? = null

    var listTugas : List<Tugas>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tugas_activity)

        set = SharePref(this)
        var id = set.getKelas()

        rcView = findViewById(R.id.rcTugas)
        rcView!!.setHasFixedSize(true)
        rcView!!.layoutManager = LinearLayoutManager(this)
        var viewModel = ViewModelProviders.of(this).get(TugasViewModel::class.java)
        viewModel.dataTugas.observeForever {
            tugasAdapter = TugasAdapter(this, it!!)
            rcView!!.adapter = tugasAdapter
        }
    }
}