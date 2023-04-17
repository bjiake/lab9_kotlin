package com.example.lab9_1.domain

interface WeatherRepository {

    suspend fun loadWeather(): List<Weather>
}