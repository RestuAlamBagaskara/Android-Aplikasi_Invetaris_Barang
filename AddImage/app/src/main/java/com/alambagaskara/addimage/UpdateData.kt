package com.alambagaskara.addimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var NamaB: EditText
    private lateinit var JumlahB: EditText
    private lateinit var BeliB: EditText
    private lateinit var JualB: EditText
    private lateinit var DeskripsiB: EditText
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        supportActionBar?.title = "Update"
        var nama = intent.getStringExtra("Barang")
        var deskripsi = intent.getStringExtra("DeskripsiBarang")

        val Nama = findViewById<TextView>(R.id.update_nama)
        val Deskripsi = findViewById<TextView>(R.id.update_deskripsi)

        Nama.text = nama
        Deskripsi.text = deskripsi

        val UpdateBtn = findViewById<Button>(R.id.update)
        UpdateBtn.setOnClickListener {

            NamaB = findViewById(R.id.update_nama)
            JumlahB = findViewById(R.id.update_jumlah)
            BeliB = findViewById(R.id.update_beli)
            JualB = findViewById(R.id.update_jual)
            DeskripsiB = findViewById(R.id.update_deskripsi)

            val NamaBarang = NamaB.text.toString()
            val JumlahBarang = JumlahB.text.toString()
            val BeliBarang = BeliB.text.toString()
            val JualBarang = JualB.text.toString()
            val DeskripsiBarang = DeskripsiB.text.toString()

            DataUpdate(NamaBarang, JumlahBarang,BeliBarang,JualBarang,DeskripsiBarang)
        }
    }

    private fun DataUpdate(NamaBarang: String, JumlahBarang: String, BeliBarang: String, JualBarang: String, DeskripsiBarang: String) {
        auth = FirebaseAuth.getInstance()
        val UID = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference(UID)
        val barang = mapOf<String,String>(
            "jumlah" to JumlahBarang,
            "beli" to BeliBarang,
            "jual" to JualBarang,
            "deskripsi" to DeskripsiBarang
        )

        database.child(NamaBarang).updateChildren(barang).addOnSuccessListener {
            Toast.makeText(this, "Data Berhasil di Update", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@UpdateData, DrawerActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal Update Data", Toast.LENGTH_SHORT).show()
        }
    }
}