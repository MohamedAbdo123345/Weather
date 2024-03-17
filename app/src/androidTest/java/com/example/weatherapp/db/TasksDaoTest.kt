package com.example.weatherapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.dto.LocationDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {
    @get:Rule
    var instantExecutorRule=InstantTaskExecutorRule()
    private lateinit var dataBase: WeatherDataBase
    @Before
    fun setUp(){
        dataBase= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).build()
    }
    @After
    fun closeDb() = dataBase.close()
    @Test
    fun insertAndGetWeather() = runBlocking{
        // Given
        val locationDTO = LocationDTO(31.3301, 33.251, "ismalii")

        // When

            dataBase.getWeatherDao().insert(locationDTO)
            val loaded = dataBase.getWeatherDao().getAllWeather().first()

            // Then
            assertThat(loaded.size, `is`(1))
            assertThat(loaded[0].cityName, `is`("ismalii"))
            assertThat(loaded[0].latitude, `is`(31.3301))
        }
    }

