package com.nemo.sampleweatherapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nemo.sampleweatherapp.R

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }
}