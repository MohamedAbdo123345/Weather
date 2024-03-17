package com.example.weatherapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.reposetry.WeatherRepositoryImpl

class AllWeatherViewModelFactory(private val irpo: WeatherRepositoryImpl):ViewModelProvider.Factory {
override fun <T:ViewModel> create(modelClass:Class<T>):T{
    return if(modelClass.isAssignableFrom(WeatherViewModel::class.java)) {

        WeatherViewModel(irpo) as T
    }else{
        throw IllegalArgumentException("View Model class not found")
    }
}
}