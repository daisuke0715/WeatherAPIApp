package com.nemo.sampleweatherapp.viewModel.weather

import androidx.lifecycle.*
import com.nemo.sampleweatherapp.model.repository.WeatherRepository
import kotlinx.coroutines.launch
import com.nemo.sampleweatherapp.extensions.StateData.Companion.Status.SUCCESSFUL
import com.nemo.sampleweatherapp.extensions.StateData.Companion.Status.ERROR
import java.util.*

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
                                WeatherMainData(
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

    abstract class WeatherItemModel {
        abstract fun isSameAs(itemModel: WeatherItemModel): Boolean
        abstract fun hasSameContent(itemModel: WeatherItemModel): Boolean
    }

    data class WeatherDate(
        val date: String
    ) : WeatherItemModel() {
        override fun isSameAs(itemModel: WeatherItemModel): Boolean {
            return itemModel is WeatherDate
        }

        override fun hasSameContent(itemModel: WeatherItemModel): Boolean {
            return (itemModel as? WeatherDate)?.date == date
        }
    }

    data class WeatherMainData(
        val tempMin: Double,
        val tempMax: Double,
        val mainWeather: String,
        val description: String
    ) : WeatherItemModel() {
        override fun isSameAs(itemModel: WeatherItemModel): Boolean {
            return itemModel is WeatherMainData
        }

        override fun hasSameContent(itemModel: WeatherItemModel): Boolean {
            val compareItem = itemModel as? WeatherMainData ?: return false
            return (compareItem.tempMin == tempMin
                    && compareItem.tempMax == tempMax
                    && compareItem.mainWeather == mainWeather
                    && compareItem.description == description)
        }
    }
}