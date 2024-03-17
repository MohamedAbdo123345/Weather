package com.example.weatherapp.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class LocationDTO(
    @PrimaryKey
    var latitude: Double,
    var longitude: Double,
    var cityName: String,

)
