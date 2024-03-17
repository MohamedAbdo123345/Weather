package com.example.weatherapp.network

import com.example.weatherapp.dto.Response
import com.example.weatherapp.weather.Weather

sealed class ApiState {
    class Success(val data: Response) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
}