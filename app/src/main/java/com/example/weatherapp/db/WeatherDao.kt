//package com.example.weatherapp.db
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Update
//import com.example.weatherapp.dto.LocationDTO
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.selects.select
//import retrofit2.http.Query
//@Dao
//interface WeatherDao {
//    @Query("SELECT * FROM Weather limit 1")
//    fun getWeather(): Flow<LocationDTO>
//    @Insert
//    fun insert(locationDTO: LocationDTO)
//    @Update
//    fun update(locationDTO: LocationDTO)
//    @Delete
//    fun delete(locationDTO: LocationDTO): Int
//}
package com.example.weatherapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.dto.LocationDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM Weather ")
    fun getAllWeather(): Flow<List<LocationDTO>>


    @Insert
    fun insert(locationDTO: LocationDTO)

    @Update
    fun update(locationDTO: LocationDTO)

    @Delete
    fun delete(locationDTO: LocationDTO): Int
}
