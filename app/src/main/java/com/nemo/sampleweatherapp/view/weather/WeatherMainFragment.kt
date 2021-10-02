package com.nemo.sampleweatherapp.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nemo.sampleweatherapp.databinding.FragmentWeatherMainBinding

class WeatherMainFragment: Fragment() {
    private var _binding: FragmentWeatherMainBinding? = null
    private val binding: FragmentWeatherMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}