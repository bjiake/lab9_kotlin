package com.example.lab9_1.data.local

import androidx.room.*

@Dao
interface WeatherDao {
    @Insert
    suspend fun saveWeather(vararg weather: WeatherEntity)

    @Query(value = "Select * from WeatherEntity")
    suspend fun getAllWeather() : WeatherEntity

    @Query("SELECT COUNT(*) FROM WeatherEntity WHERE dtTxt = :date")
    suspend fun getWeatherCountByDate(date: String): Int
}