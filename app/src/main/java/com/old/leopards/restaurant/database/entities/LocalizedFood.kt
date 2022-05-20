package com.old.leopards.restaurant.database.entities

import java.math.BigDecimal

data class LocalizedFood (
    val id: Int,
    val name: String,
    val description: String,
    val portionSize: Int,
    val price: Int,
    val photoLink: String
) {}