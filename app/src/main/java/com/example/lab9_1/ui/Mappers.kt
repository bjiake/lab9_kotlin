package com.example.lab9_1.ui

import com.example.lab9_1.domain.model.City
import com.example.lab9_1.domain.model.Weather
import com.example.lab9_1.ui.Model.CityUI
import com.example.lab9_1.ui.Model.WeatherUI

internal fun City.toUI(): CityUI = CityUI(cityName = cityName)

internal fun List<Weather>.toUI(): List<WeatherUI> = map {
    it.toUI()
}

internal fun Weather.toUI(): WeatherUI = WeatherUI(
    dtTxt = dtTxt, temp = temperature, pressure = pressure, icon = iconURL
)