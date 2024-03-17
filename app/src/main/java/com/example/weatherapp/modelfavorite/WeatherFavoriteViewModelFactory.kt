package com.example.weatherapp.modelfavorite

import WeatherFavoriteViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.reposetry.WeatherRepositoryImpl

class WeatherFavoriteViewModelFactory (private val irpo: WeatherRepositoryImpl):
    ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass:Class<T>):T{
        return if(modelClass.isAssignableFrom(WeatherFavoriteViewModel::class.java)) {

            WeatherFavoriteViewModel(irpo) as T
        }else{
            throw IllegalArgumentException("View Model class not found")
        }
    }
}