package com.mauk.absen.model

import com.google.gson.annotations.SerializedName

class Tugas {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("nama_guru")
    lateinit var nama_guru: String
    @SerializedName("kelas_id")
    lateinit var kelas_id: String
    @SerializedName("tugas")
    lateinit var tugas: String
    @SerializedName("file")
    lateinit var file: String
    @SerializedName("kelas")
    lateinit var kelas: Kelas
    constructor(){
    }
    constructor(id: String, nama_guru: String, kelas_id: String, tugas: String, file: String, kelas: Kelas){
        this.id = id
        this.nama_guru = nama_guru
        this.kelas_id = kelas_id
        this.tugas = tugas
        this.file = file
        this.kelas = kelas
    }
}