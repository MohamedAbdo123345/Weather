package com.example.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset

class Convertr {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun convertUnixTimeToHour(unixTimestamp: Long): String {
            val instant = Instant.ofEpochSecond(unixTimestamp)
            val time = LocalTime.ofInstant(instant, ZoneOffset.UTC)
            return "${time.hour}:${time.minute}${time.second}"
        }
    }
}