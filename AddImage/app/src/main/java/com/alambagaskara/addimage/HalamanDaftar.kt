package com.alambagaskara.addimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class HalamanDaftar : AppCompatActivity() {

    //Deklarasi variabel
    private lateinit var user : EditText
    private lateinit var email: EditText
    private lateinit var password1: EditText
    private lateinit var password2: EditText
    private lateinit var signup: Button
    private lateinit var to_signin: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_daftar)

        //Inisialisasi variabel
        auth = FirebaseAuth.getInstance()
        user = findViewById(R.id.input_user)
        email = findViewById(R.id.input_email)
        password1 = findViewById(R.id.input_password1)
        password2 = findViewById(R.id.input_password2)
        signup = findViewById(R.id.daftar)

        //Menyembunyikan ActionBar
        supportActionBar?.hide()

        signup.setOnClickListener {
            signUpUser()
        }
//        to_signin.setOnClickListener {
//            startActivity(Intent(this, HalamanLogin::class.java))
//            finish()
//        }
    }

    
    private fun signUpUser(){
        if (email.text.toString().isEmpty()){
            email.error = "Masukkan Email"
            email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            email.error = "Format Salah"
            email.requestFocus()
            return
        }
        if (password1.text.toString().isEmpty()){
            password1.error = "Masukkan Password"
            password1.requestFocus()
            return
        }
//        if (password1.text.toString() == password2.text.toString()){
//            password2.error = "Password Tidak Sama"
//            password2.requestFocus()
//            return
//        }

        auth.createUserWithEmailAndPassword(email.text.toString(), password1.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HalamanLogin::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext,"Gagal mendaftar", Toast.LENGTH_SHORT).show()
                }
            }
    }
}