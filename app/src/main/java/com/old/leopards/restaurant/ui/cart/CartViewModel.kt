package com.old.leopards.restaurant.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.api.CurrencyApi
import com.old.leopards.restaurant.api.CurrencyService
import com.old.leopards.restaurant.models.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

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

    fun fetchCurrency(currencyApi: CurrencyApi) {
        compositeDisposable.add(
            currencyApi.getUSDCurrency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currency ->
                    Log.d("Текущий курс: ", currency.value.toString())
                }, { error ->
                    println("Пиздец: $error")
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
