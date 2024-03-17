package com.example.weatherapp.network

import com.example.weatherapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//https://openweathermap.org/forecast5



object RetrofitHelper{
    const val  BASE_URL="http://api.openweathermap.org/data/2.5/"
    val retrofit= Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.Base_Url)
        .build()
    val retrofitservice: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}