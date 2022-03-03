package com.alambagaskara.addimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailList : AppCompatActivity() {

    private  lateinit var database : DatabaseReference
    private lateinit var storage : StorageReference
    private lateinit var auth : FirebaseAuth
//    companion object{
//        const val EXTRA_IMAGE = "extra image"
//        const val EXTRA_NAME = "extra nama"
//        const val EXTRA_JUMLAH = "extra jumlah"
//        const val EXTRA_BELI = "extra beli"
//        const val EXTRA_JUAL = "extra jual"
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_list)

        supportActionBar?.hide()

        var gambar = intent.getStringExtra("GAMBAR")
        var nama = intent.getStringExtra("NAMA")
        var jumlah = intent.getStringExtra("JUMLAH")
        var beli = intent.getStringExtra("BELI")
        var jual = intent.getStringExtra("JUAL")
        var deskripsi = intent.getStringExtra("DESKRIPSI")

//        val gambarDosen:ImageView = findViewById(R.id.image_detail)
//        val nama: TextView = findViewById(R.id.detail_nama)
//        val jumlah: TextView = findViewById(R.id.detail_jumlah)
//        val beli: TextView = findViewById(R.id.detail_beli)
//        val jual: TextView = findViewById(R.id.detail_jual)
//
//        val gambar = intent.getIntExtra(EXTRA_IMAGE,0)
//        val namaBar = intent.getStringExtra(EXTRA_NAME)
//        val jumlahBar = intent.getStringExtra(EXTRA_JUMLAH)
//        val beliBar = intent.getStringExtra(EXTRA_BELI)
//        val jualBar = intent.getStringExtra(EXTRA_JUAL)

        val Gambar = findViewById<ImageView>(R.id.image_detail)
        val Nama = findViewById<TextView>(R.id.detail_nama)
        val Jumlah = findViewById<TextView>(R.id.detail_jumlah)
        val Beli = findViewById<TextView>(R.id.detail_beli)
        val Jual = findViewById<TextView>(R.id.detail_jual)
        val Deskripsi = findViewById<TextView>(R.id.detail_deskripsi)

        Glide.with(this)
            .load(gambar)
            .apply(RequestOptions())
            .into(Gambar)
        Nama.text = nama
        Jumlah.text = jumlah
        Beli.text = beli
        Jual.text = jual
        Deskripsi.text = deskripsi

        val btn_Edit = findViewById<Button>(R.id.edit_button)
        btn_Edit.setOnClickListener {
            val moveUpdate = Intent(this, UpdateData::class.java)
            moveUpdate.putExtra("Barang", Nama.text)
            moveUpdate.putExtra("DeskripsiBarang", Deskripsi.text)
            startActivity(moveUpdate)
            finish()
        }

        val btn_delete = findViewById<Button>(R.id.delete_button)
        btn_delete.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var UID = auth.currentUser?.uid.toString()
            database = FirebaseDatabase.getInstance().getReference(UID)
            storage = FirebaseStorage.getInstance().getReference(UID)
            storage.child(Nama.text.toString()+".jpeg").delete()
            database.child(Nama.text.toString()).removeValue().addOnSuccessListener {
                Toast.makeText(this,"Data Telah Dihapus", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@DetailList, DrawerActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}