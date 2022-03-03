package com.alambagaskara.addimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HalamanLogin : AppCompatActivity() {

    //Deklarasi variabel
    private lateinit var auth: FirebaseAuth
    private lateinit var user: EditText
    private lateinit var password: EditText
    private lateinit var singin: Button
    private lateinit var to_signup: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_login)


        //Inisialisasi variabel
        auth = FirebaseAuth.getInstance()
        user = findViewById(R.id.input_user)
        password = findViewById(R.id.input_password)
        singin = findViewById(R.id.login)
//        to_signup = findViewById(R.id.to_register)

        //Menyembunyikan ActionBar
        supportActionBar?.hide()

//        to_signup.setOnClickListener {
//            startActivity(Intent(this, HalamanDaftar::class.java))
//            finish()
//        }
        singin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        if (user.text.toString().isEmpty()){
            user.error = "Masukkan Email"
            user.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.text.toString()).matches()){
            user.error = "Format Salah"
            user.requestFocus()
            return
        }
        if (password.text.toString().isEmpty()){
            password.error = "Masukkan Password"
            password.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(user.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            startActivity(Intent(this@HalamanLogin, DrawerActivity::class.java))
            finish()
        }
        else{
            Log.w("TAG", "signInWithEmail:failure")
            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }
    }
}