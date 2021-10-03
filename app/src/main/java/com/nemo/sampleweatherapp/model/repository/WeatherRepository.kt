package com.nemo.sampleweatherapp.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nemo.sampleweatherapp.extensions.Errors
import com.nemo.sampleweatherapp.extensions.StateData
import com.nemo.sampleweatherapp.model.apiModel.WeatherAPIModel
import com.nemo.sampleweatherapp.model.entity.WeatherData
import kotlinx.coroutines.Dispatchers

class WeatherRepository {
    fun getFiveDayWeatherForecast(): LiveData<StateData<WeatherData>> = liveData(Dispatchers.IO) {
        val data = StateData<WeatherData>()
        emit(data.loading())

        try {
            val res = WeatherAPIModel.get().getCurrentWeather()
            if (res.isSuccessful) {
                res.body()?.let { weatherData ->
                    emit(data.success(weatherData))
                }
            } else {
                emit(data.failure(Errors.failedGetData()))
            }
        } catch (e: Exception) {
            println(e.message)
        } finally {
            if(latestValue?.status == StateData.Companion.Status.LOADING) {
                emit(data.failure(Errors.failedGetData()))
            }
        }
    }
}