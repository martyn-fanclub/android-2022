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
import kotlin.math.abs

class FoodViewModel(private val localizedFoodViewModel: LocalizedFoodViewModel) : ViewModel() {

    // List is Immutable
    private val _foodListState =
        MutableStateFlow<Pair<Int, List<Food>>>(Pair(0, CopyOnWriteArrayList()))
    val foodListState = _foodListState

    private val _pageState = MutableStateFlow(0)
    val pageState = _pageState

    val foodPerPage = 10


    fun nextPage() {
        viewModelScope.launch {
            var update = abs(_foodListState.value.first) + 1
            val list = _foodListState.value.second
            if (list.drop(update * foodPerPage).isEmpty()) {
                update = -update
            }
//            if (list.take((update + 1) * foodPerPage).isEmpty()) {
//                update = -update
//            }
            _foodListState.emit(Pair(update, list))
        }
    }

    fun prevPage() {
        viewModelScope.launch {
            var update = abs(_foodListState.value.first)
            if (update != 0) {
                update -= 1
            }
            val list = _foodListState.value.second
            _foodListState.emit(Pair(update, list))
        }
    }

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
                foodListState.emit(Pair(1, food))
            }
        }
    }
}
