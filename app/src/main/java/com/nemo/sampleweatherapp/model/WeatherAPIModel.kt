package com.nemo.sampleweatherapp.model

import com.nemo.sampleweatherapp.BuildConfig
import com.nemo.sampleweatherapp.model.entity.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherAPIModel {
    interface GetWeatherInterface {
        @GET("data/2.5/forecast")
        suspend fun getCurrentWeather(
            @Query("q") cityName: String = "London",
            @Query("appId") APIKey: String = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
            @Query("units") units: String = "metric",
            @Query("lang") language: String = "ja"
        ): Response<WeatherData>
    }
}