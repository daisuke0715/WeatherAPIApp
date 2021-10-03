package com.nemo.sampleweatherapp.viewModel.weather

import androidx.lifecycle.*
import com.nemo.sampleweatherapp.model.repository.WeatherRepository
import kotlinx.coroutines.launch
import com.nemo.sampleweatherapp.extensions.StateData.Companion.Status.SUCCESSFUL
import com.nemo.sampleweatherapp.extensions.StateData.Companion.Status.ERROR

class WeatherMainViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _weatherForecastLD = MutableLiveData<List<WeatherItemModel>>()
    val weatherForecastLD: LiveData<List<WeatherItemModel>>
        get() = _weatherForecastLD

    fun getFiveDayWeatherForecast(): MediatorLiveData<Void> {
        val mediator = MediatorLiveData<Void>()

        viewModelScope.launch {
            mediator.addSource(weatherRepository.getFiveDayWeatherForecast()) { result ->
                when(result.status) {
                    SUCCESSFUL -> {
                        val weatherItemModelList = mutableListOf<WeatherItemModel>()

                        result.data?.list?.forEach { hourlyWeather ->
                            weatherItemModelList.add(WeatherDate(hourlyWeather.dt_txt))

                            val weather = hourlyWeather.weather.firstOrNull() ?: return@forEach
                            weatherItemModelList.add(
                                WeatherCellData(
                                    tempMin = hourlyWeather.main.temp_min,
                                    tempMax = hourlyWeather.main.temp_max,
                                    mainWeather = weather.main,
                                    description = weather.description
                                )
                            )
                        }

                        _weatherForecastLD.postValue(weatherItemModelList)
                    }
                    ERROR -> {
                        _weatherForecastLD.postValue(listOf())
                    }
                    else -> { }
                }
            }
        }

        return mediator
    }

    abstract class WeatherItemModel

    data class WeatherDate(
        val date: String
    ) : WeatherItemModel()

    data class WeatherCellData(
        val tempMin: Double,
        val tempMax: Double,
        val mainWeather: String,
        val description: String
    ) : WeatherItemModel()
}