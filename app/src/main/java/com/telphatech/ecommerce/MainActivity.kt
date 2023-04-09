package com.telphatech.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.telphatech.ecommerce.activities.LoginActivity
import com.telphatech.ecommerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var i =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)

        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        binding.bottomBar.onItemSelected = {
            when(it) {
                0 -> {
                    i=0
                    navController.navigate(R.id.homeFragment)
                }
                1 -> i = 1
                2 -> i = 2
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = when (destination.id) {
                R.id.cartFragment -> "My Cart"
                R.id.profileFragment -> "My Dashboard"
                else -> "Ecommerce"

            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if(i==0) {
            finish()
        }
    }
}