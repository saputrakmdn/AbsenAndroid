package com.mauk.absen.service

import com.mauk.absen.model.Absen
import com.mauk.absen.model.Siswa
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @POST("login")
    @FormUrlEncoded
    fun login(@Field("nis")nis: String): Call<Siswa>
    @POST("absenmasuk")
    @FormUrlEncoded
    fun masuk(@Field("tanggal")tanggal: String,
              @Field("id")id: String,
              @Field("kelas")kelas: String): Call<Absen>
    @POST("absenpulang")
    @FormUrlEncoded
    fun pulang(@Field("id_absen")id_absen: String): Call<Absen>
}