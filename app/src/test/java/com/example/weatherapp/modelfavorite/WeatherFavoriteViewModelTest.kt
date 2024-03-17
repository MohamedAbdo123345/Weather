package com.example.weatherapp.modelfavorite

import WeatherFavoriteViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.db.FakeWeatherLocalDatasourceImpl
import com.example.weatherapp.dto.Current
import com.example.weatherapp.dto.LocationDTO
import com.example.weatherapp.dto.Response
import com.example.weatherapp.network.FakeWeatherRemoteDatasourceImp
import com.example.weatherapp.reposetry.FakeReposetry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeatherFavoriteViewModelTest{
    @ExperimentalCoroutinesApi
    @get:Rule
    val rule= InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val fav1= LocationDTO(30.0,30.2,"cairo")
    val fav2= LocationDTO(20.0,20.2,"alex")
    val fav3= LocationDTO(40.0,40.2,"masr")
    val waether1= Response(
        current= Current(clouds = 0, sunrise = 0, temp = 0.0,visibility=null, uvi = null,
            pressure =0, feelsLike = null, windDeg = null, windGust = null,dt=null, dewPoint = null,
            sunset = null, weather = null,
            humidity = 0, windSpeed = 0.0),
        timezone= "",
        timezoneOffset= null,
        daily = null,
        lon = null,
        hourly = null,
        lat = null
    )
    private val local= listOf(fav1,fav2,fav3)
    private val remote=waether1
   lateinit var repo: FakeReposetry
    lateinit var favViewModel: WeatherFavoriteViewModel
    private lateinit var remoteDatasourceImp: FakeWeatherRemoteDatasourceImp
    private lateinit var localDatasourceImpl: FakeWeatherLocalDatasourceImpl
    @Before
    fun setUp() {
        remoteDatasourceImp = FakeWeatherRemoteDatasourceImp(remote)
        localDatasourceImpl= FakeWeatherLocalDatasourceImpl(local as MutableList<LocationDTO>)
        repo= FakeReposetry(remoteDatasourceImp,localDatasourceImpl)
        favViewModel=WeatherFavoriteViewModel(repo)

    }
   
}