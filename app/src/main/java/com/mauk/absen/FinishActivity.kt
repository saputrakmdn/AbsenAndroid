package com.mauk.absen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.about_activity.*

class FinishActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
        igSmk.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/aboutsmkn5kab.tng/"))
            startActivity(i)
        }
        igTkj.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/kabartkj/"))
            startActivity(i)
        }
        ig.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/s.kmdn/"))
            startActivity(i)
        }
    }
}