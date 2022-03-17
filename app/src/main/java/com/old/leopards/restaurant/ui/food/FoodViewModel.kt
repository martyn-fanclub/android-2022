package com.old.leopards.restaurant.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    /*
    TODO
      Использовать MutableStateFlow https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
      Для автоматической подгрузки блюд и категорий при скролле вниз
    */
    init {
        viewModelScope.launch {
            //TODO
        }
    }
}