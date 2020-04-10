package com.mauk.absen.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mauk.absen.R
import com.mauk.absen.adapter.DetailAbsenAdapter
import com.mauk.absen.viewmodel.AbsenViewModel
import kotlinx.android.synthetic.main.detail_absen_activity.*
import java.util.*

class DetailAbsenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_absen_activity)
        var rcview: RecyclerView = findViewById(R.id.rcAbsen)
        rcview.setHasFixedSize(true)
        val bulan = android.text.format.DateFormat.format("MMMM", Date())
        month.text = bulan
        rcview.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProviders.of(this).get(AbsenViewModel::class.java)
        viewModel.dataTugas.observeForever {
            val detailAdapet = DetailAbsenAdapter(it!!, this)
            rcview.adapter = detailAdapet
        }
    }
}