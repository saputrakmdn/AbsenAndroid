package com.mauk.absen.service

import com.mauk.absen.model.Absen
import com.mauk.absen.model.Piket
import com.mauk.absen.model.Siswa
import com.mauk.absen.model.Tugas
import retrofit2.Call
import retrofit2.http.*

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
    @GET("tugas/{id}")
    fun tugas(@Path("id")id: String): Call<List<Tugas>>
    @GET("absen/{id}")
    fun absen(@Path("id")id: String): Call<List<Absen>>
    @GET("profile/{id}")
    fun piket(@Path("id")id: String): Call<Siswa>
}