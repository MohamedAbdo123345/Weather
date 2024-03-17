package com.example.weatherapp.db

import android.content.Context
import android.util.Log
import com.example.weatherapp.dto.LocationDTO
import kotlinx.coroutines.flow.Flow

class WeatherLocalDatasourceImpl (private val dao: WeatherDao):WeatherLocalDatasource {
    companion object {
        private var instance: WeatherLocalDatasourceImpl? = null
        fun getInstance(context: Context?): WeatherLocalDatasourceImpl {
            return instance ?: synchronized(this) {
                val database = WeatherDataBase.getInstance(context!!)
                val dao = database.getWeatherDao()
                val temInstance = WeatherLocalDatasourceImpl(dao)
                instance = temInstance
                temInstance
            }
        }
    }


    override fun insertWeatherToFavorite(locationDTO: LocationDTO) {
        if (locationDTO != null)

            dao.insert(locationDTO)
    }

    override fun deletFavoriteWeather(locationDTO: LocationDTO): Int {
        if (locationDTO != null) {
            return dao.delete(locationDTO)
        }
        return 0
    }

    override fun getAllWeatherFromFavorite(): Flow<List<LocationDTO>> {
        return dao.getAllWeather()
        Log.i("TAG", "getAllWeatherFromFavorite:$dao.getAllWeather() ")
    }

    override fun getUpdate(locationDTO: LocationDTO) {
        if (locationDTO != null)
          return  dao.update(locationDTO)
    }
}