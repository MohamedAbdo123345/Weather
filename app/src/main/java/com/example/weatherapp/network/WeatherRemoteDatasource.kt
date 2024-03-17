package com.example.weatherapp.network

import com.example.weatherapp.dto.Response
import com.example.weatherapp.weather.Weather
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface WeatherRemoteDatasource {
     suspend fun getProduct(
       lat:Double,
       lon:Double,
       units:String,
       lang:String,
    ): Response
}