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
            _languageViewModel.addAllLanguages(
                Language(name = "Русский"),
                Language(name = "English")
            )
        }
    }

    private fun initFood() {
        runBlocking {
            val f1 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 10,
                    price = 15,
                    photoLink = "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
            val f2 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 15,
                    price = 20,
                    photoLink = "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
            val f3 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 20,
                    price = 30,
                    photoLink = "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f1.toInt(),
                    name = "Продукт1",
                    description = "Описание1"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f1.toInt(),
                    name = "Food1",
                    description = "Description1"
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f2.toInt(),
                    name = "Продукт2",
                    description = "Описание2"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f2.toInt(),
                    name = "Food2",
                    description = "Description2"
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f3.toInt(),
                    name = "Продукт3",
                    description = "Описание3"
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f3.toInt(),
                    name = "Food3",
                    description = "Description3"
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
