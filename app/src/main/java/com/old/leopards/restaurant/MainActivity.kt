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
            val desciption =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu turpis molestie, dictum est a, mattis tellus. Sed dignissim, metus nec fringilla accumsan, risus sem sollicitudin lacus, ut interdum tellus elit sed risus. \n"

            val f0 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 10,
                    price = 15,
                    photoLink = "https://vilkin.pro/wp-content/uploads/2019/11/rizotto-s-ovoshami.jpg"
                )
            )
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
                    photoLink = "https://i.pinimg.com/originals/58/1a/28/581a288ae9ad427627108404d79b56fd.jpg"
                )
            )
            val f3 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 20,
                    price = 30,
                    photoLink = "https://avatars.mds.yandex.net/get-zen_doc/40274/pub_59828b67b2d0090abe598ece_59828bac1410c371db766837/scale_1200"
                )
            )
            val f4 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 21,
                    price = 1,
                    photoLink = "https://attuale.ru/wp-content/uploads/2018/02/maxresdefault-126.jpg"
                )
            )
            val f5 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 22,
                    price = 2,
                    photoLink = "https://kuhnya-s-papoi.ru/wp-content/uploads/2020/08/3-10.jpg"
                )
            )
            val f6 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 23,
                    price = 3,
                    photoLink = "https://img.taste.com.au/0Nzctw6S/taste/2017/08/ramen-129052-2.jpg"
                )
            )
            val f7 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 24,
                    price = 4,
                    photoLink = "https://forexdengi.com/attachment.php?attachmentid=4557701&d=1612425173"
                )
            )
            val f8 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 25,
                    price = 5,
                    photoLink = "https://kartinkin.net/uploads/posts/2021-07/thumbs/1627626017_27-kartinkin-com-p-kruassan-s-nachinkoi-yeda-krasivo-foto-34.jpg"
                )
            )
            val f9 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 26,
                    price = 6,
                    photoLink = "https://history-doc.ru/wp-content/uploads/2021/12/salat-s-kuritsej-i-yajtsom.jpg"
                )
            )
            val f10 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 27,
                    price = 7,
                    photoLink = "https://kipmu.ru/wp-content/uploads/2021/02/hinkali.jpg"
                )
            )
            val f11 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 28,
                    price = 8,
                    photoLink = "https://31tv.ru/wp-content/uploads/2021/08/pell.jpg"
                )
            )
            val f12 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 29,
                    price = 9,
                    photoLink = "https://pigmine.ru/wp-content/uploads/6/1/b/61b3a1798ffdb6f9d8b64d4631719deb.jpeg"
                )
            )
            val f13 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 30,
                    price = 10,
                    photoLink = "https://s0.rbk.ru/v6_top_pics/resized/1440xH/media/img/7/06/756306662116067.jpg"
                )
            )
            val f14 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 31,
                    price = 11,
                    photoLink = "https://street-foods.ru/wp-content/uploads/2021/04/%D0%9C%D1%8F%D1%81%D0%BE-%D0%B4%D0%BB%D1%8F-%D1%88%D0%B0%D1%88%D0%BB%D1%8B%D0%BA%D0%B0-1.jpg"
                )
            )
            val f15 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 32,
                    price = 12,
                    photoLink = "https://about-tea.ru/wp-content/uploads/7/9/4/794a5992bfa9b5762dad053125514346.jpeg"
                )
            )
            val f16 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 33,
                    price = 13,
                    photoLink = "https://pic2.canyin375.com/uploads/allimg/150303/5-150303102239107.png"
                )
            )
            val f17 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 34,
                    price = 14,
                    photoLink = "https://pigmine.ru/wp-content/uploads/e/7/e/e7e61be4a7fef093747be186cb8db7db.jpeg"
                )
            )
            val f18 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 35,
                    price = 15,
                    photoLink = "https://пицца-бест.рф/800/600/https/static.tildacdn.com/tild3361-3365-4565-b836-323461376537/yandex-eda-RIBA13.jpg"
                )
            )
            val f19 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 36,
                    price = 16,
                    photoLink = "https://s5o.ru/storage/simple/cyber/edt/51/d5/82/63/cybere1b6862847e.jpg"
                )
            )
            val f20 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 37,
                    price = 17,
                    photoLink = "https://phonoteka.org/uploads/posts/2021-04/1618495820_52-p-fon-shaurma-58.jpg"
                )
            )
            val f21 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 38,
                    price = 18,
                    photoLink = "https://recept-borscha.ru/wp-content/uploads/f/d/1/fd19571275ccf562cdf02f4cd5ff81cc.jpeg"
                )
            )
            val f22 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 39,
                    price = 19,
                    photoLink = "https://стар-фуд.рф/wp-content/uploads/2021/06/food_343441.jpg"
                )
            )
            val f23 = _foodItemViewModel.addFoodItem(
                FoodItem(
                    portionSize = 40,
                    price = 20,
                    photoLink = "https://bestvietnam.ru/wp-content/uploads/2020/03/%D0%B1%D1%83%D1%80%D1%80%D0%B8%D1%82%D0%BE.jpg"
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f0.toInt(),
                    name = "ПельмениПельмениПельмениПельмениПельмениПельмениПельмениПельмениПельмени",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f0.toInt(),
                    name = "ПельмениПельмениПельмениПельмениПельмениПельмениПельмениПельмениПельмени",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f1.toInt(),
                    name = "Ризотто с вареными яйцами",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f1.toInt(),
                    name = "Risotto with boiled eggs",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f2.toInt(),
                    name = "Пицца",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f2.toInt(),
                    name = "Pizza",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f3.toInt(),
                    name = "Рис",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f3.toInt(),
                    name = "Rice",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f4.toInt(),
                    name = "Яйца Пашот",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f4.toInt(),
                    name = "Poached Eggs",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f5.toInt(),
                    name = "Омлет с зеленью",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f5.toInt(),
                    name = "Omelet with herbs",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f6.toInt(),
                    name = "Рамен",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f6.toInt(),
                    name = "Ramen",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f7.toInt(),
                    name = "Фо Бо",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f7.toInt(),
                    name = "Pho Bo",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f8.toInt(),
                    name = "Круассан",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f8.toInt(),
                    name = "Croissant",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f9.toInt(),
                    name = "Салат Цезарь",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f9.toInt(),
                    name = "Caesar salad",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f10.toInt(),
                    name = "Хинкали",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f10.toInt(),
                    name = "Khinkali",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f11.toInt(),
                    name = "Пельмени",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f11.toInt(),
                    name = "Dumplings",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f12.toInt(),
                    name = "Манты",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f12.toInt(),
                    name = "Manti",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f13.toInt(),
                    name = "Хачапури",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f13.toInt(),
                    name = "Khachapuri",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f14.toInt(),
                    name = "Шашлык",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f14.toInt(),
                    name = "Kebab",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f15.toInt(),
                    name = "Окрошка с квасом",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f15.toInt(),
                    name = "Okroshka with kvass",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f16.toInt(),
                    name = "Малатан",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f16.toInt(),
                    name = "Malatan",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f17.toInt(),
                    name = "Борщ",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f17.toInt(),
                    name = "Borsch",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f18.toInt(),
                    name = "Финская уха",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f18.toInt(),
                    name = "Finnish ukha",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f19.toInt(),
                    name = "Баоцзы",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f19.toInt(),
                    name = "Baozi",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f20.toInt(),
                    name = "Шаверма",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f20.toInt(),
                    name = "Shawarma",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f21.toInt(),
                    name = "Мисо суп",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f21.toInt(),
                    name = "Miso soup",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f22.toInt(),
                    name = "Сельдь под шубой",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f22.toInt(),
                    name = "Herring under a fur coat",
                    description = desciption
                )
            )

            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 1,
                    foodItemId = f23.toInt(),
                    name = "Буррито",
                    description = desciption
                )
            )
            _foodDescriptionViewModel.addFoodDescription(
                FoodDescription(
                    languageId = 2,
                    foodItemId = f23.toInt(),
                    name = "Burrito",
                    description = desciption
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
