package com.nemo.sampleweatherapp.model.apiModel

import com.nemo.sampleweatherapp.BuildConfig
import com.nemo.sampleweatherapp.manager.RetrofitManager
import com.nemo.sampleweatherapp.model.entity.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherAPIModel {
    fun get(): GetWeatherInterface = RetrofitManager.weatherRetrofit.create(GetWeatherInterface::class.java)

    interface GetWeatherInterface {
        @GET("data/2.5/forecast")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String = "Tokyo",
            @Query("appId") APIKey: String = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "ja"
        ): Response<WeatherData>
    }
}