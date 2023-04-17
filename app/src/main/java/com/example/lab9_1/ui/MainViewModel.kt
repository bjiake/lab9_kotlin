package com.example.lab9_1.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab9_1.App
import com.example.lab9_1.Constants
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.data.local.WeatherDao
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherNW
import com.example.lab9_1.data.toDomain
import com.example.lab9_1.data.toEntity
import com.example.lab9_1.domain.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(weatherAPI: WeatherAPI) : ViewModel() {
    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList get() = _weatherList
    private var _weatherAPI: WeatherAPI

    init {
        _weatherAPI = weatherAPI
        loadWeather()
    }

    private fun loadWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = App.api.getForecast(
                    Constants.API_CITY,
                    Constants.API_KEY,
                    Constants.API_UNITS,
                    Constants.API_LANG
                )
                if (response.isSuccessful) {
                    val weather = response.body()?.list?.map {
                        it.toDomain()
                    }
                    _weatherList.postValue(weather)
                    val cachedWeather = App.dataBase.weatherDao().getAllWeather()

                    weather?.forEach {
                        val date = it.dtTxt.split(" ")[0]

                        val isDatabaseContainsThisWeather =
                            cachedWeather.any { it.dtTxt.split(" ")[0] == date }

                        if (!isDatabaseContainsThisWeather) {
                            App.dataBase.weatherDao().saveWeather(it.toEntity())
                        }
                    }
                } else {
                    //Если бд нет ничего не отображать
                    _weatherList.postValue(
                        App.dataBase.weatherDao().getAllWeather().map { it.toDomain() }
                    )
                }
            } catch (e: HttpException) {
                _weatherList.postValue(emptyList())
                Log.e("aaa", "Exception ${e.message}")
            } catch (e: Throwable) {
                _weatherList.postValue(
                    App.dataBase.weatherDao().getAllWeather().map { it.toDomain() }
                )
            }
        }
    }
}