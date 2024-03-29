package com.example.weatherapp.network

import com.example.weatherapp.Constants
import com.example.weatherapp.dto.Response
import com.example.weatherapp.weather.City
import com.example.weatherapp.weather.Weather
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiServices {
    @GET("onecall?")
    suspend fun getWeather(@Query("lat") lat: Double?,
                                  @Query("lon") lon: Double?,
                           @Query("units") units:String,
                         @Query("lang") lang:String,
                    @Query("appid") appId:String=Constants.APPID,
                                  ): Response

}