package com.example.weatherapp.reposetry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.db.FakeWeatherLocalDatasourceImpl
import com.example.weatherapp.dto.LocationDTO
import com.example.weatherapp.dto.Response
import com.example.weatherapp.network.FakeWeatherRemoteDatasourceImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TestRepositoryImpl {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val localSourcePlacesList = mutableListOf(
        LocationDTO(0.0, 0.0, "place1"),
        LocationDTO(0.1, 0.1, "place2")
    )
    val remoteSourceResponse = Response(
        null, null, 5, null, null, null, null
    )
    private lateinit var fakeRemoteDataSource: FakeWeatherRemoteDatasourceImp
    private lateinit var fakeLocalDataSource: FakeWeatherLocalDatasourceImpl
    //class under Test
    private lateinit var tasksRepository: FakeReposetry
    lateinit var result: Response

    @Before
    fun setUp() {
        fakeLocalDataSource = FakeWeatherLocalDatasourceImpl(localSourcePlacesList)
        fakeRemoteDataSource = FakeWeatherRemoteDatasourceImp(remoteSourceResponse)
        tasksRepository = FakeReposetry(fakeRemoteDataSource, fakeLocalDataSource)
    }

    @Test
    fun addPlaceToFav_place_dpupdate() = runBlockingTest {
        // Given
        val place = LocationDTO(0.0, 0.0, "place3")
        // When
        tasksRepository.insertWeather(place)

        var result: List<LocationDTO>? = null
        tasksRepository.getLocalAllWeatherFav().collect {
            result = it
        }
        // Then
        MatcherAssert.assertThat(result?.get(2)?.cityName, CoreMatchers.`is`("place3"))
    }
    @Test
    fun getAllFAvPlaces_Favaces()= runBlockingTest {
        //When
        var result= listOf<LocationDTO>()
        tasksRepository.getLocalAllWeatherFav().collect{
            result=it
        }
        //Return
        Assert.assertThat(result,CoreMatchers.`is`(localSourcePlacesList))
    }
    @Test
    fun getWeatherData() = runBlockingTest {
        // Given
        val lat = "55.55"
        val lon = "44.44"
        val units="metric"
        val lang="en"

        // When
        val response = tasksRepository.getWeather(lat.toDouble(), lon.toDouble(),units.toString(), lang.toString()).first()

        // Then
        assertThat(response, CoreMatchers.`is`(remoteSourceResponse))
    }
}
