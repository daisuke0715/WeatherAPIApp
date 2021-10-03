package com.nemo.sampleweatherapp.model.entity

data class WeatherDescription(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)