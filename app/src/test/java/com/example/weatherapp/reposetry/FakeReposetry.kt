package com.example.weatherapp.reposetry

import com.example.weatherapp.db.FakeWeatherLocalDatasourceImpl
import com.example.weatherapp.db.WeatherLocalDatasourceImpl
import com.example.weatherapp.dto.LocationDTO
import com.example.weatherapp.dto.Response
import com.example.weatherapp.network.FakeWeatherRemoteDatasourceImp
import com.example.weatherapp.network.WeatherRemoteDatasourceImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeReposetry (private val weatherRemoteDatasourceImp: FakeWeatherRemoteDatasourceImp,
                                        private val weatherLocalDatasourceImpl: FakeWeatherLocalDatasourceImpl

) :Reposetry {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Flow<Response> {
        return flow {
            val response = weatherRemoteDatasourceImp.getProduct(lat, lon, units, lang)
            emit(response)
        }    }

    override suspend fun getLocalAllWeatherFav(): Flow<List<LocationDTO>> {
        return weatherLocalDatasourceImpl.getAllWeatherFromFavorite()
    }

    override suspend fun insertWeather(locationDTO: LocationDTO) {
        weatherLocalDatasourceImpl.insertWeatherToFavorite(locationDTO)
    }

    override suspend fun deletWeather(locationDTO: LocationDTO): Flow<Int> {
        val result = weatherLocalDatasourceImpl.deletFavoriteWeather(locationDTO)
        return if (result > 0) {
            flow { emit(1) }
        } else {
            flow { emit(0) }
        }    }
}