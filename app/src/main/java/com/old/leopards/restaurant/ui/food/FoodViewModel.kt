package com.old.leopards.restaurant.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.viewModels.LocalizedFoodViewModel
import com.old.leopards.restaurant.models.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.concurrent.CopyOnWriteArrayList

class FoodViewModel(private val localizedFoodViewModel: LocalizedFoodViewModel) : ViewModel() {

    // List is Immutable
    private val _foodListState = MutableStateFlow<List<Food>>(CopyOnWriteArrayList())
    val foodListState = _foodListState


    init {
        viewModelScope.launch {
            localizedFoodViewModel.getLocalizedFoodByLanguageId.collect { it ->
                val food = it.map {
                    Food(
                        it.id.toLong(),
                        it.name,
                        it.description,
                        it.portionSize,
                        BigDecimal(it.price),
                        it.photoLink
                    )
                }
                foodListState.emit(food)
            }
        }
    }
}
