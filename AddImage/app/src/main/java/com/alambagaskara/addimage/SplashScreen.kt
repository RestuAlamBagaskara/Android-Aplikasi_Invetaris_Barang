package com.alambagaskara.addimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //Menyembunyikan Action Bar
        supportActionBar?.hide()

        //Memberi jeda pada splashScreen beberapa waktu lalu berpindah ke halaman Login
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HalamanAwal::class.java))
            finish()
        }, 4000)
    }
}