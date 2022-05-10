package com.old.leopards.restaurant.ui.cart

import android.annotation.SuppressLint
import android.icu.util.Currency
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.api.CurrencyApi
import com.old.leopards.restaurant.models.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class CartViewModel : ViewModel() {
    /*
    TODO
      Использовать MutableStateFlow https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
      Для автоматической подгрузки товаров в корзине при скролле вниз
    */

    private val _cartUiState = MutableStateFlow<CartUiState>(CartUiState.Empty)
    val cartUiState: StateFlow<CartUiState> = _cartUiState

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun fetchCurrency(currencyApi: CurrencyApi) {
        val locale: String = "RUB_" + Currency.getInstance(Locale.getDefault()).currencyCode
        val price = 150 // В рубасах
        compositeDisposable.add(
            currencyApi.getUSDCurrency(locale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currency ->
                    val cur = currency.results[locale]
                    if (cur != null) {
                        Log.d("Текущая сумма", (cur.value * price).toString() +  " " + cur.to)
                    }
                }, { error ->
                    Log.e("Упс", error.localizedMessage!!)
                })
        )
    }

    init {
        viewModelScope.launch {
            val f: MutableList<Pair<Food, Int>> = ArrayList()
            for (i in 0..10) {
                f.add(
                    Pair(
                        Food(
                            i.toLong(),
                            "Ризотто с вареными яйцами ",
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu turpis molestie, dictum est a, mattis tellus. Sed dignissim, metus nec fringilla accumsan, risus sem sollicitudin lacus, ut interdum tellus elit sed risus. \n",
                            100,
                            BigDecimal.TEN,
                            R.drawable.rizotto
                        ), i
                    )
                )
            }
            _cartUiState.value = CartUiState.HasFood(f)
        }
    }

    sealed class CartUiState() {
        object Empty : CartUiState()
        data class HasFood(val food: MutableList<Pair<Food, Int>>) : CartUiState()
    }

}
