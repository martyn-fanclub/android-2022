package com.old.leopards.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.old.leopards.restaurant.databinding.ActivityMainBinding
import io.paperdb.Paper
import com.old.leopards.restaurant.databinding.FragmentProfileBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var _binding_profile: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView

        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                ?.findNavController()!!
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_food, R.id.navigation_cart, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Initializing Paper db, which is used for cart view presentation
        Paper.init(applicationContext)
    }


    fun activate(fragmentProfileBinding: FragmentProfileBinding) {
        this._binding_profile = fragmentProfileBinding
    }

    fun editProfileName(name: String) {
        this.runOnUiThread {
            _binding_profile.username.text = name
        }
    }

    fun editProfileEmail(email: String) {
        this.runOnUiThread {
            _binding_profile.email.text = email
        }
    }
}
