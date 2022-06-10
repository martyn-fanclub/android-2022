package com.old.leopards.restaurant.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class CurrencyService {
    private val secretKey: String = "3eb02a38e6ba6222cf84"
    private val baseUrl: String = "https://www.cbr-xml-daily.ru/"
    private val contentType = "application/json".toMediaType()
    private val retrofit = createRetrofit()
    val api: CurrencyApi = retrofit.create(CurrencyApi::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .client(createOkHttpClient())
            .build()
    }
}
