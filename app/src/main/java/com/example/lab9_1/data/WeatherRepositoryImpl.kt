package com.example.lab9_1.data

import android.util.Log
import com.example.lab9_1.Constants
import com.example.lab9_1.Constants.API_KEY
import com.example.lab9_1.Constants.API_LANG
import com.example.lab9_1.Constants.API_UNITS
import com.example.lab9_1.data.local.CitySW
import com.example.lab9_1.data.local.PositionSW
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.local.WeatherSW
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.domain.WeatherData
import com.example.lab9_1.domain.WeatherRepository
import com.example.lab9_1.domain.model.Position

sealed interface WhereGetWeatherData {
    data class FromNetWork(val weather: WeatherData) : WhereGetWeatherData
    data class FromDataBase(val weather: WeatherData): WhereGetWeatherData
}

class WeatherRepositoryImpl(
    private val weatherAPI: WeatherAPI,
    private val dataBase: WeatherDataBase
) : WeatherRepository {

    override suspend fun loadWeather(lat: String, lon: String): WhereGetWeatherData {
        try {
//            val response = weatherAPI.getForecast(
//                lat,
//                lon,
//                Constants.API_KEY,
//                Constants.API_UNITS,
//                Constants.API_LANG
//            )
            val weather1 = weatherAPI.getForecast(lat, lon, API_KEY, API_UNITS, API_LANG)
            updateWeatherDB(weather1.list.toSW(), weather1.city.toSW(), Position(lat, lon).toSW())
            return WhereGetWeatherData.FromNetWork(weather1.toDomain())
//            val weather = response.body()?.list?.map {
//                it.toDomain()
//            }
//
//            val cachedWeather = dataBase.weatherDao().getAllWeather()
//
//            weather?.forEach {
//                val date = it.dtTxt.split(" ")[0]
//
//                val isDatabaseContainsThisWeather =
//                    cachedWeather.any { it.dtTxt.split(" ")[0] == date }
//
//                if (!isDatabaseContainsThisWeather) {
//                    dataBase.weatherDao().saveWeather(it.toEntity())
//                }
//            }
//            return WhereGetWeatherData.FromNetWork(WeatherData(weather.orEmpty()))
//            return weather.orEmpty()
        } catch (e: java.lang.Exception) {
            Log.e("aaa", "Exception ${e.message}")
//            val weather = dataBase.weatherDao().getAllWeather().map { it.toDomain() }
            val weather = WeatherData(
                getWeatherDB().toDomain(),
                getCityDB().toDomain(),
                getPositionDB().toDomain()
            )
            //SnackBar один раз показался
            //sealed класс внутри 2 дата класса с инета или бд
            return WhereGetWeatherData.FromDataBase(weather)
//            return weather
        }
    }
    private suspend fun getPositionDB() = dataBase.weatherDao().getPosition()

    private suspend fun getCityDB() = dataBase.weatherDao().getCity()

    private suspend fun getWeatherDB() = dataBase.weatherDao().getWeather()

    private suspend fun updateWeatherDB(
        weatherList: List<WeatherSW>,
        cityName: CitySW,
        position: PositionSW
    ) =
        dataBase.weatherDao().updateWeather(weatherList, cityName, position)
}