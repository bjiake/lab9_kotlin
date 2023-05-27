package com.example.lab9_1.domain.model

data class Weather(
    val temperature: Double,
    val pressure: Int,
    val dtTxt: String,
    val iconURL: String
)
