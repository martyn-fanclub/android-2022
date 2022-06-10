package com.old.leopards.restaurant.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.api.CurrencyService
import com.old.leopards.restaurant.api.models.CurrencyResponse
import com.old.leopards.restaurant.models.Cart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    /*
    TODO
      Использовать MutableStateFlow https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
      Для автоматической подгрузки товаров в корзине при скролле вниз
    */

    private val _cartUiState = MutableStateFlow<CartUiState>(CartUiState.Empty)
    val cartUiState: StateFlow<CartUiState> = _cartUiState

    private val _currencyState = MutableStateFlow<CurrencyUiState>(
        CurrencyUiState.Error(
            CurrencyResponse(emptyMap())
        )
    )
    val currencyState: StateFlow<CurrencyUiState> = _currencyState
    private val currencyService = CurrencyService()

    fun loadFromBD() {
        viewModelScope.launch {
            _cartUiState.update { CartUiState.HasFood(Cart.getCart()) }
        }
    }

    fun fetchCurrency() {
        viewModelScope.launch {
            _currencyState.update { CurrencyUiState.Success(currencyService.api.getUSDCurrency()) }
        }
    }

    sealed class CartUiState {
        object Empty : CartUiState()
        data class HasFood(val food: MutableList<Cart.CartItem>) : CartUiState()
    }

    sealed class CurrencyUiState {
        data class Success(val currency: CurrencyResponse) : CurrencyUiState()
        data class Error(val currency: CurrencyResponse) : CurrencyUiState()
    }

}
