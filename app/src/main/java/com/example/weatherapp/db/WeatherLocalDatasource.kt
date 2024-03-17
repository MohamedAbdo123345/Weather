package com.example.weatherapp.db

import com.example.weatherapp.dto.LocationDTO
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDatasource {
    fun insertWeatherToFavorite(locationDTO: LocationDTO);
    fun deletFavoriteWeather(locationDTO: LocationDTO):Int
    fun getAllWeatherFromFavorite(): Flow<List<LocationDTO>>
    fun getUpdate(locationDTO: LocationDTO)
}