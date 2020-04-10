package com.mauk.absen.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mauk.absen.R
import com.mauk.absen.model.Absen

class DetailAbsenAdapter: RecyclerView.Adapter<DetailAbsenAdapter.DetailAbsenViewHolder> {
    var listAbsen: List<Absen>
    var mContext: Context
    constructor(listAbsen: List<Absen>, mContext: Context){
        this.listAbsen = listAbsen
        this.mContext = mContext
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailAbsenViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_absen, p0, false)
        return  DetailAbsenViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAbsen.size
    }

    override fun onBindViewHolder(p0: DetailAbsenViewHolder, p1: Int) {
        val absen = listAbsen[p1]
        p0.tanggal.text = absen.tanggal
        p0.ket.text = absen.keterangan
    }

    class DetailAbsenViewHolder(item: View): RecyclerView.ViewHolder(item) {
        var tanggal : TextView
        var ket : TextView
        init {
            tanggal = item.findViewById(R.id.tanggal)
            ket = item.findViewById(R.id.ket)
        }
    }
}