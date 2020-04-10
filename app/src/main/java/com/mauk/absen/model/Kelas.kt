package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Kelas {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("nama_kelas")
    lateinit var nama_kelas: String
    constructor(){}
    constructor(id: String, nama_kelas: String){
        this.id = id
        this.nama_kelas = nama_kelas
    }
}