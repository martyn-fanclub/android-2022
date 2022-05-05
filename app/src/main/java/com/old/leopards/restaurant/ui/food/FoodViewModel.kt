package com.old.leopards.restaurant.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.models.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.concurrent.CopyOnWriteArrayList

class FoodViewModel : ViewModel() {

    // List is Immutable
    private val _foodListState = MutableStateFlow<List<Food>>(CopyOnWriteArrayList<Food>())
    val foodListState = _foodListState

    init {
        viewModelScope.launch {
            foodListState.update {
                it + Food(
                    0,
                    "Ризотто с вареными яйцами",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu turpis molestie, dictum est a, mattis tellus. Sed dignissim, metus nec fringilla accumsan, risus sem sollicitudin lacus, ut interdum tellus elit sed risus. \n",
                    100,
                    BigDecimal(100),
                    R.drawable.rizotto
                )
            }
            _foodListState.update {
                it + Food(
                    0,
                    "[etnnj] с вареными яйцами",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu turpis molestie, dictum est a, mattis tellus. Sed dignissim, metus nec fringilla accumsan, risus sem sollicitudin lacus, ut interdum tellus elit sed risus. \n",
                    100,
                    BigDecimal(100),
                    R.drawable.rizotto
                )
            }
        }
    }
}