package com.old.leopards.restaurant.api.models

import com.google.gson.annotations.SerializedName

data class CurrencyItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("val")
    val value: Double,
    @SerializedName("fr")
    val fr: String,
    @SerializedName("to")
    val to: String
)
