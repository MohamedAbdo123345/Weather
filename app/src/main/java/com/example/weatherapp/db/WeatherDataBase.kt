
package com.example.weatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.dto.LocationDTO

@Database(entities = arrayOf(LocationDTO::class), version = 1 )
abstract class WeatherDataBase:RoomDatabase() {
    abstract fun getWeatherDao():WeatherDao
    companion object {
        @Volatile
        private var INSTANCE: WeatherDataBase? = null
        fun getInstance (ctx: Context): WeatherDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, WeatherDataBase::class.java, "Weather_database")
                    .build()
                INSTANCE = instance
// return instance
                instance }
        }


    }

}


//package com.example.weatherapp.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.weatherapp.dto.LocationDTO
//
//@Database(entities = [LocationDTO::class], version = 1, exportSchema = false)
//abstract class WeatherDataBase : RoomDatabase() {
//    abstract fun getWeatherDao(): WeatherDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: WeatherDataBase? = null
//
//        fun getInstance(context: Context): WeatherDataBase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext, WeatherDataBase::class.java, "weather_database"
//                )
//                    .fallbackToDestructiveMigration() // Handle schema changes with destructive migration
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
