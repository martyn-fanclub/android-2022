package com.old.leopards.restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val query: CurrencyCount,
    val results: Map<String, CurrencyItem>
)

@Serializable
data class CurrencyCount(val count: Int)
