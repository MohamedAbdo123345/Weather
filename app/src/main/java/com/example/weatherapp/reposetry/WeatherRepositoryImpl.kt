package com.example.weatherapp.reposetry

import android.annotation.SuppressLint
import android.util.Log
import com.example.weatherapp.db.WeatherLocalDatasourceImpl
import com.example.weatherapp.dto.LocationDTO
import com.example.weatherapp.dto.Response
import com.example.weatherapp.network.WeatherRemoteDatasourceImp
import com.example.weatherapp.weather.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class WeatherRepositoryImpl private constructor
    (private val weatherRemoteDatasourceImp: WeatherRemoteDatasourceImp,
            private val weatherLocalDatasourceImpl: WeatherLocalDatasourceImpl
)
    :Reposetry{

companion object{
    private var instance:WeatherRepositoryImpl?=null
    fun getInstance(
        weatherRemoteDatasourceImp: WeatherRemoteDatasourceImp,
        weatherLocalDatasourceImpl: WeatherLocalDatasourceImpl

    ):WeatherRepositoryImpl{
    return instance?:synchronized(this){
      val temp=WeatherRepositoryImpl(weatherRemoteDatasourceImp,weatherLocalDatasourceImpl)
       instance=temp
                temp
         }
    }
}

    override suspend fun getWeather(lat: Double, lon: Double, units: String, lang: String): Flow<Response> {
        return flowOf(weatherRemoteDatasourceImp.getProduct(lat,lon,units,lang))
        Log.i("TAG", "getWeather:from prosetry ")}

    override suspend fun getLocalAllWeatherFav(): Flow<List<LocationDTO>> {
        return weatherLocalDatasourceImpl.getAllWeatherFromFavorite()
    }

    override suspend fun insertWeather(locationDTO: LocationDTO) {
        weatherLocalDatasourceImpl.insertWeatherToFavorite(locationDTO)
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun deletWeather(locationDTO: LocationDTO):Flow <Int> = flow {
     val rowDelet =  weatherLocalDatasourceImpl.deletFavoriteWeather(locationDTO)
        emit(rowDelet)
    }


}

