package com.example.lab9_1.domain

import com.example.lab9_1.data.WhereGetWeatherData

interface WeatherRepository {
    suspend fun loadWeather(): WhereGetWeatherData
}