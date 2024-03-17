package com.example.weatherapp.reposetry

import com.example.weatherapp.dto.LocationDTO
import com.example.weatherapp.dto.Response
import com.example.weatherapp.weather.Weather
import kotlinx.coroutines.flow.Flow

interface Reposetry {
     suspend fun getWeather(lat: Double, lon: Double, units: String, lang: String): Flow<Response>
     suspend fun getLocalAllWeatherFav(): Flow<List<LocationDTO>>
     suspend fun insertWeather(locationDTO: LocationDTO)
     suspend fun deletWeather(locationDTO: LocationDTO):Flow<Int>
}