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
    private val baseUrl: String = "https://free.currconv.com/api/v7/"
    private val contentType = "application/json".toMediaType()
    private val retrofit = createRetrofit()
    val api: CurrencyApi = retrofit.create(CurrencyApi::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request();
            val url: HttpUrl =
                original.url.newBuilder().addQueryParameter("apiKey", secretKey).build()
            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        })
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(createOkHttpClient())
            .build()
    }
}
