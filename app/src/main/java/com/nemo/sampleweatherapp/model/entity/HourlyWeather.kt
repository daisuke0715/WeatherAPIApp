package com.nemo.sampleweatherapp.model.entity

data class HourlyWeather(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<WeatherDescription>,
    val wind: Wind
)