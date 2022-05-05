package com.old.leopards.restaurant.models

import androidx.annotation.DrawableRes
import java.math.BigDecimal

data class Food(
    val id: Long,
    val title: String,
    val description: String,
    val weight: Int,
    val price: BigDecimal,
    @DrawableRes
    val img: Int?
)