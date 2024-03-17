package com.example.weatherapp.db

import com.example.weatherapp.dto.LocationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeWeatherLocalDatasourceImpl( private val fakeDataList: MutableList<LocationDTO> = mutableListOf()):WeatherLocalDatasource {

    override fun insertWeatherToFavorite(locationDTO: LocationDTO) {
        fakeDataList.add(locationDTO)
    }

    override fun deletFavoriteWeather(locationDTO: LocationDTO): Int {
        val removed = fakeDataList.remove(locationDTO)
        return if (removed) 1 else 0
    }

    override fun getAllWeatherFromFavorite(): Flow<List<LocationDTO>> {
        return flowOf(fakeDataList)
    }

    override fun getUpdate(locationDTO: LocationDTO) {
        TODO("Not yet implemented")
    }
}