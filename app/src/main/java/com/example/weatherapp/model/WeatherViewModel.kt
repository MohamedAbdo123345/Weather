package com.example.weatherapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.dto.Response
import com.example.weatherapp.network.ApiState
import com.example.weatherapp.reposetry.WeatherRepositoryImpl
import com.example.weatherapp.weather.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WeatherViewModel(private val irpo: WeatherRepositoryImpl): ViewModel() {
    private var _weatherItem= MutableStateFlow<ApiState>(ApiState.Loading)
      val weather=_weatherItem.asStateFlow()
    init {
     //   fatchDate()
    }
    fun fatchDate(lat: Double, lon: Double, units: String, lang: String){
        viewModelScope.launch(Dispatchers.IO) {
            irpo.getWeather(lat,lon,units,lang)
                .catch {e->
                    _weatherItem.value=ApiState.Failure(e)
                    Log.i("TAG", "fatchDateerror: ${e.message}")
                }
                .collect{result->
                    _weatherItem.value=ApiState.Success(result)
                    Log.i("TAG", "fatchDate: ")
                }
//

        }
    }
}