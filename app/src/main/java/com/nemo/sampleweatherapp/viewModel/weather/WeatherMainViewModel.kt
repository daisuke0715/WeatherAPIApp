package com.nemo.sampleweatherapp.viewModel.weather

import androidx.lifecycle.ViewModel
import com.nemo.sampleweatherapp.model.repository.WeatherRepository

class WeatherMainViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

}