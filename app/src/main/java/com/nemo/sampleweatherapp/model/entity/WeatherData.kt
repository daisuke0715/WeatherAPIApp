package com.nemo.sampleweatherapp.model.entity

data class WeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<HourlyWeather>,
    val message: Int
)