package com.old.leopards.restaurant.api

import com.old.leopards.restaurant.api.models.CurrencyDTO
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CurrencyApi {
    @GET("convert?q=USD_RUB&compact=ultra")
    @Headers("Content-Type: application/json")
    fun getUSDCurrency(): Single<CurrencyDTO>
}