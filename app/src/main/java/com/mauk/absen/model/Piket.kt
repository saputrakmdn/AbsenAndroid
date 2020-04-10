package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Piket {
@SerializedName("id")
lateinit var id: String
    @SerializedName("siswa_id")
    lateinit var siswa_id: String
    @SerializedName("kelas_id")
    lateinit var kelas_id: String
    @SerializedName("kelas")
    lateinit var kelas: Kelas
    @SerializedName("siswa")
    lateinit var siswa: Siswa
}