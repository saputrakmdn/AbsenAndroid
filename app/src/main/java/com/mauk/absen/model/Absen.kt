package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Absen {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("tanggal")
    lateinit var tanggal :String
    @SerializedName("jam_masuk")
    lateinit var jam_masuk: String
    @SerializedName("jam_pulang")
    lateinit var jam_pulang: String
    @SerializedName("siswa_id")
    lateinit var siswa_id: String
    @SerializedName("kelas_id")
    lateinit var kelas_id: String

    constructor(){}
    constructor(tanggal: String, pegawai: String){
        this.tanggal = tanggal
        this.siswa_id = pegawai
    }
}