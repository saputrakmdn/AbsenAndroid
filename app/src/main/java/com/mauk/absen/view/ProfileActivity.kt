package com.mauk.absen.view

import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mauk.absen.R
import com.mauk.absen.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.profile_activity.*

class ProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        var viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.dataPiket.observeForever {
            nama.text = it!!.nama
            nis.text = it.nis
            jenis.text = it.jeniskelamin
            kelas.text = it.kelas.nama_kelas
            jurusan.text = it.jurusan.kode_jurusan
            tempat.text = it.tempat
            tanggal.text = it.tanggallahir
            nohp.text = it.nohp
            val b = it.foto
            var url = "http://192.168.112.94:8000/fotosiswa/${b}"

            Glide.with(this).load(url).listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e!!.cause
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loading.visibility = View.GONE
                    main.visibility = View.VISIBLE
                    return false
                }

            }).into(foto)
            }
        }
    }
