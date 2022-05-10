package com.old.leopards.restaurant.api.models

import com.google.gson.annotations.SerializedName

data class CurrencyDTO(
    @SerializedName("USD_RUB")
    val value: Double
)
