package com.old.leopards.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.old.leopards.restaurant.data.Preferences
import com.old.leopards.restaurant.database.entities.FoodDescription
import com.old.leopards.restaurant.database.entities.FoodItem
import com.old.leopards.restaurant.database.entities.Language
import com.old.leopards.restaurant.database.viewModels.FoodDescriptionViewModel
import com.old.leopards.restaurant.database.viewModels.FoodItemViewModel
import com.old.leopards.restaurant.database.viewModels.LanguageViewModel
import com.old.leopards.restaurant.databinding.ActivityMainBinding
import com.old.leopards.restaurant.databinding.FragmentProfileBinding
import io.paperdb.Paper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var _binding_profile: FragmentProfileBinding

    private lateinit var _foodItemViewModel: FoodItemViewModel
    private lateinit var _foodDescriptionViewModel: FoodDescriptionViewModel
    private lateinit var _languageViewModel: LanguageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _foodItemViewModel = ViewModelProvider(this)[FoodItemViewModel::class.java]
        _foodDescriptionViewModel = ViewModelProvider(this)[FoodDescriptionViewModel::class.java]
        _languageViewModel = ViewModelProvider(this)[LanguageViewModel::class.java]

        initData()

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

    private fun initData() {
        val pref = Preferences(applicationContext)
        GlobalScope.launch {
            if (pref.isFirstLaunch.first()) {
                initLanguage()
                initFood()
                pref.setFirstLaunch(false)
            }
        }
    }

    private fun initLanguage() {
        runBlocking {
            _languageViewModel.addAllLanguages(Language(0, "Русский"), Language(0, "English"))
        }
    }

    private fun initFood() {
        runBlocking {
            val f1 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    0,
                    10,
                    15,
                    "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
            val f2 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    0,
                    15,
                    20,
                    "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
            val f3 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    0,
                    20,
                    30,
                    "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    0,
                    1,
                    f1.toInt(),
                    "Продукт1",
                    "Описание1"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    0,
                    2,
                    f1.toInt(),
                    "Food1",
                    "Description1"
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    0,
                    1,
                    f2.toInt(),
                    "Продукт2",
                    "Описание2"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    0,
                    2,
                    f2.toInt(),
                    "Food2",
                    "Description2"
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    0,
                    1,
                    f3.toInt(),
                    "Продукт3",
                    "Описание3"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    0,
                    2,
                    f3.toInt(),
                    "Food3",
                    "Description3"
                )
            )

        }

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
