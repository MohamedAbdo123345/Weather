package com.example.weatherapp.network

import android.content.Context
import android.util.Log
import com.example.weatherapp.dto.Response
import com.example.weatherapp.weather.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRemoteDatasourceImp private  constructor(private val service:ApiServices):WeatherRemoteDatasource{
    companion object{
        private var instance:WeatherRemoteDatasourceImp?=null
        fun getInstance(context: Context?):WeatherRemoteDatasourceImp{
            return instance?: synchronized(this){
                val retrofitservice=RetrofitHelper.retrofitservice
                val tempinstance=WeatherRemoteDatasourceImp(retrofitservice)
                instance=tempinstance
                tempinstance
            }
        }

    }

    override suspend fun getProduct(lat: Double, lon: Double, unit: String, lang: String): Response {
        Log.i("TAG", "getProduct235513215: ")
        return service.getWeather(lat, lon, unit, lang)
          //  Log.i("TAG", "getProduct5252525: from remotedatasource")}
    }
}