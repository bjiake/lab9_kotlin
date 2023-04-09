package com.example.lab9_1.domain

data class Weather(
    val temperature: Double,
    val pressure: Int,
    val dtTxt: String,
    val iconURL: String
)
