package com.old.leopards.restaurant.api

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


internal class CurrencyService {
    private val secretKey: String = "3eb02a38e6ba6222cf84"
    private val baseUrl: String = "https://free.currconv.com/api/v7/"
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
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}