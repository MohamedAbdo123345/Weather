package com.example.weatherapp.network

import com.example.weatherapp.dto.Response

class FakeWeatherRemoteDatasourceImp(private val response : Response):WeatherRemoteDatasource {
    override suspend fun getProduct(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Response {
        return response
    }
}