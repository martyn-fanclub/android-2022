package com.old.leopards.restaurant.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.api.CurrencyService
import com.old.leopards.restaurant.api.models.CurrencyCount
import com.old.leopards.restaurant.api.models.CurrencyResponse
import com.old.leopards.restaurant.models.Food
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

    private val _currencyState = MutableStateFlow<CurrencyUiState>(
        CurrencyUiState.Success(
            CurrencyResponse(CurrencyCount(0), emptyMap())
        )
    )
    val currencyState: StateFlow<CurrencyUiState> = _currencyState
    private val currencyService = CurrencyService()

    private fun fetchCurrency() {
        viewModelScope.launch {
            val cur = "RUB_USD";
            try {
                _currencyState.value =
                    CurrencyUiState.Success(currencyService.api.getUSDCurrency(cur))
                when (currencyState.value) {
                    is CurrencyUiState.Success -> {
                        val currency = (currencyState.value as CurrencyUiState.Success).currency;
                        Log.d(
                            "Success:",
                            "Data was fetched: ${currency.results[cur]?.value} ${currency.results[cur]?.to}"
                        )
                    }
                    is CurrencyUiState.Error -> Log.e(
                        "Error",
                        "${(currencyState.value as CurrencyUiState.Error).exception}"
                    )
                }
            } catch (e: Exception) {
                Log.e("Error", "$e")
            }
        }
    }

    init {
        fetchCurrency()
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
                            null
                        ), i
                    )
                )
            }
            _cartUiState.value = CartUiState.HasFood(f)
        }
    }

    sealed class CartUiState {
        object Empty : CartUiState()
        data class HasFood(val food: MutableList<Pair<Food, Int>>) : CartUiState()
    }

    sealed class CurrencyUiState {
        data class Success(val currency: CurrencyResponse) : CurrencyUiState()
        data class Error(val exception: Throwable) : CurrencyUiState()
    }

}
