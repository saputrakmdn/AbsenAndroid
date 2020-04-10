package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Jurusan {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("nama")
    lateinit var nama: String
    @SerializedName("kode_jurusan")
    lateinit var kode_jurusan: String
}