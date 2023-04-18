package com.example.lab9_1.data

import android.util.Log
import com.example.lab9_1.Constants
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.domain.Weather
import com.example.lab9_1.domain.WeatherRepository
import com.google.android.material.snackbar.Snackbar


class WeatherRepositoryImpl(
    private val dataBase: WeatherDataBase,
    private val weatherAPI: WeatherAPI
) : WeatherRepository {
    override suspend fun loadWeather(): List<Weather> {
        try {
            val response = weatherAPI.getForecast(
                Constants.API_CITY,
                Constants.API_KEY,
                Constants.API_UNITS,
                Constants.API_LANG
            )

            val weather = response.body()?.list?.map {
                it.toDomain()
            }

            val cachedWeather = dataBase.weatherDao().getAllWeather()

            weather?.forEach {
                val date = it.dtTxt.split(" ")[0]

                val isDatabaseContainsThisWeather =
                    cachedWeather.any { it.dtTxt.split(" ")[0] == date }

                if (!isDatabaseContainsThisWeather) {
                    dataBase.weatherDao().saveWeather(it.toEntity())
                }
            }
            return weather.orEmpty()

        } catch (e: java.lang.Exception) {
            Log.e("aaa", "Exception ${e.message}")
            val weather = dataBase.weatherDao().getAllWeather().map { it.toDomain() }
            //SnackBar один раз показался
            //sealed класс внутри 2 дата класса с инета или бд
            return weather
        }
    }
}