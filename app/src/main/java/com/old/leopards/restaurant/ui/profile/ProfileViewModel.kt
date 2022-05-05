package com.old.leopards.restaurant.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    /*
    TODO
      Использовать MutableStateFlow https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
      Для автоматической подгрузки прошлых заказов при скролле вниз
    */
    init {
        viewModelScope.launch {
            //TODO
        }
    }
}
