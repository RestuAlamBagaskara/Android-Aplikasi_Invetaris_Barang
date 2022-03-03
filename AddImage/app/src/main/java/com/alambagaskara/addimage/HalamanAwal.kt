package com.alambagaskara.addimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HalamanAwal : AppCompatActivity() {

    private lateinit var login : Button
    private lateinit var daftar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_awal)

        supportActionBar?.hide()

        login = findViewById(R.id.login)
        login.setOnClickListener {
            startActivity(Intent(this@HalamanAwal, HalamanLogin::class.java))
            finish()
        }

        daftar = findViewById(R.id.daftar)
        daftar.setOnClickListener {
            startActivity(Intent(this@HalamanAwal, HalamanDaftar::class.java))
            finish()
        }
    }
}