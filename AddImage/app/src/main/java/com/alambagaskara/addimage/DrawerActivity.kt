package com.alambagaskara.addimage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.alambagaskara.addimage.databinding.ActivityDrawerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.content_drawer.*
import kotlinx.android.synthetic.main.nav_header_drawer.*

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDrawer.toolbar)

        binding.appBarDrawer.fab.setOnClickListener {
            startActivity(Intent(this@DrawerActivity, HalamanInput::class.java))
            finish()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listFragment, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout -> {
                    Firebase.auth.signOut()
                    startActivity(Intent(this@DrawerActivity, HalamanAwal::class.java))
                    finish()
                    true
                }
                else -> return@setNavigationItemSelectedListener true
            }
        }

        auth = FirebaseAuth.getInstance()
        val namaEmail = Firebase.auth.currentUser?.email
        val nav = navView.getHeaderView(0)
        val currentUser = nav.findViewById<TextView>(R.id.currentEmail)
        currentUser.text = namaEmail.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.option_menu, menu)
//        return true
//    }
}