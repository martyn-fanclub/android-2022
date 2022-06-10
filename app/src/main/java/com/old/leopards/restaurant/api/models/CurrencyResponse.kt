package com.old.leopards.restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val rates: Map<String, Double>
)
