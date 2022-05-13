package com.old.leopards.restaurant.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyItem(
    val id: String,
    @SerialName("val")
    val value: Double,
    val fr: String,
    val to: String
)
