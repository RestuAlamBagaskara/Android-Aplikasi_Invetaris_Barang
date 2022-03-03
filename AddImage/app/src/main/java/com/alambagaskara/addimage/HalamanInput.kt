package com.alambagaskara.addimage

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.alambagaskara.addimage.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask

class HalamanInput : AppCompatActivity(){
    //variabel penampung gambar yang akan dipilih
//    private lateinit var binding: ActivityHalamanInputBinding
    private var imgPath: Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUpload: StorageTask<*>? = null
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_input)

//        val binding = ActivityHalamanInputBinding.inflate(layoutInflater)
//        setContentView(binding.root)



        supportActionBar?.title = "Tambah Barang"

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            val preview = findViewById<ImageView>(R.id.preview)
            imgPath = result.data?.data
            preview.setImageURI(imgPath)
        }

        //set Data


        auth = FirebaseAuth.getInstance()
        val UID = Firebase.auth.currentUser?.uid.toString()

        mStorageRef = FirebaseStorage.getInstance().getReference(UID)
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(UID)


        val tambahGambar:Button = findViewById(R.id.input_gambar)
        tambahGambar.setOnClickListener {
            val galeri = Intent( )
            galeri.type = "image/*"
            galeri.action = Intent.ACTION_GET_CONTENT
            resultLauncher.launch(galeri)
        }

        val upload = findViewById<Button>(R.id.upload)
        upload.setOnClickListener {

//            val upload = Barang(
//                        photo = imgPath.toString(),
//                        Nama = input_nama.text!!.toString().trim(),
//                        jumlah = input_jumlah.text!!.toString().trim(),
//                        beli = input_beli.text!!.toString().trim(),
//                        jual = input_jual.text!!.toString().trim(),
//                        deskripsi = input_deskripsi.text!!.toString().trim()
//                    )
//            mDatabaseRef = FirebaseDatabase.getInstance().getReference("DataUser")
//            mDatabaseRef!!.child(Nama).setValue(upload).addOnSuccessListener {
//                Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
//            }.addOnFailureListener {
//                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
//            }
            uploadFile()
        }
    }

    private fun uploadFile() {
        if (imgPath != null) {
            val nama = findViewById<EditText>(R.id.input_nama)
            val Jumlah = findViewById<EditText>(R.id.input_jumlah)
            val Beli = findViewById<EditText>(R.id.input_beli)
            val Jual = findViewById<EditText>(R.id.input_jual)
            val Deskripsi = findViewById<EditText>(R.id.input_deskripsi)

            val Nama = nama.text!!.toString().trim()
            val jumlah = Jumlah.text!!.toString().trim()
            val beli = Beli.text!!.toString().trim()
            val jual = Jual.text!!.toString().trim()
            val deskripsi = Deskripsi.text!!.toString().trim()


            val fileReference = mStorageRef!!.child(
                nama.text!!.toString().trim()+"."+"jpeg"
            )
            mUpload = fileReference.putFile(imgPath!!)
                .addOnSuccessListener {
                    Toast.makeText(this@HalamanInput, "Berhasil Diunggah", Toast.LENGTH_SHORT).show()
                    val upload = Barang(
                        imgPath.toString(),
                        Nama,
                        jumlah,
                        beli,
                        jual,
                        deskripsi
                    )
                    mDatabaseRef!!.child(Nama).setValue(upload)
                    fileReference.downloadUrl.addOnSuccessListener {

                        mDatabaseRef!!.child(Nama).child("photo").setValue(it.toString())

                    }
                    openImageActivity()
                }
        }else{
            Toast.makeText(this@HalamanInput, "Gambar Belum dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImageActivity() {
        startActivity(Intent(this@HalamanInput, DrawerActivity::class.java))
        finish()
    }

//    private fun pilihFile() {
//        val galeri = Intent( )
//        galeri.type = "image*//*"
//        galeri.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(galeri, PICK_IMAGE_REQUEST)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && requestCode == Activity.RESULT_OK
//            && data != null && data.data != null)
//            {
//            imgPath = data.data
//            preview.setImageURI(imgPath)
//        }
//    }

//    private fun getFileExtensions(uri: Uri):String?{
//        val cr = contentResolver
//        val mime = MimeTypeMap.getSingleton()
//        return mime.getExtensionFromMimeType(cr.getType(uri))
//    }
}