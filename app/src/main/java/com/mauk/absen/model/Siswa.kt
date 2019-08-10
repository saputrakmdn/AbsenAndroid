package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Siswa {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("nis")
    lateinit var nis: String
    @SerializedName("nama")
    lateinit var nama: String
    @SerializedName("kelas_id")
    lateinit var kelas_id: String
    @SerializedName("created_at")
    lateinit var created_at: String
    @SerializedName("updated_at")
    lateinit var updated_at: String

    constructor(){}
    constructor(nis: String, nama: String){
        this.nis = nis
        this.nama = nama
    }
}