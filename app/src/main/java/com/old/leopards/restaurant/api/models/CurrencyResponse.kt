package com.old.leopards.restaurant.api.models

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("results")
    val results: Map<String, CurrencyItem>
)
