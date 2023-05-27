package com.example.lab9_1.domain

import com.example.lab9_1.domain.model.City
import com.example.lab9_1.domain.model.Position
import com.example.lab9_1.domain.model.Weather

data class WeatherData(
    val weatherList: List<Weather>,
    val city: City,
    val position: Position
)
