package com.nemo.sampleweatherapp.manager

import android.app.Application
import com.nemo.sampleweatherapp.model.repository.WeatherRepository
import com.nemo.sampleweatherapp.viewModel.weather.WeatherMainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }

    private val appModules = module {
        viewModel { WeatherMainViewModel(get()) }

        single { WeatherRepository() }
    }
}