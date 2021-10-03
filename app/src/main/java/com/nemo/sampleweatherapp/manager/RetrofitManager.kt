package com.nemo.sampleweatherapp.manager

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    private val gson = GsonBuilder()
        .serializeNulls()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    private val httpBuilder: OkHttpClient.Builder
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    //header
                    val request = original.newBuilder()
                        .header("content-type", "application/json")
                        .method(original.method(), original.body())
                        .build()

                    return@Interceptor chain.proceed(request)
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        }

    private const val weatherUrl = "https://api.openweathermap.org"
    val weatherRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(weatherUrl)
        .client(httpBuilder.build())
        .build()
}