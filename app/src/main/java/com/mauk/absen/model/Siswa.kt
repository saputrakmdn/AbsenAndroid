package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Siswa {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("nis")
    lateinit var nis: String
    @SerializedName("nama")
    lateinit var nama: String
    @SerializedName("jeniskelamin")
    lateinit var jeniskelamin: String
    @SerializedName("tempat")
    lateinit var tempat : String
    @SerializedName("tanggallahir")
    lateinit var tanggallahir: String
    @SerializedName("nohp")
    lateinit var nohp: String
    @SerializedName("foto")
    lateinit var foto: String
    @SerializedName("kelas_id")
    lateinit var kelas_id: String
    @SerializedName("created_at")
    lateinit var created_at: String
    @SerializedName("updated_at")
    lateinit var updated_at: String
    @SerializedName("kelas")
    lateinit var kelas: Kelas
    @SerializedName("jurusan")
    lateinit var jurusan: Jurusan

    constructor(){}
    constructor(nis: String, nama: String){
        this.nis = nis
        this.nama = nama
    }
}