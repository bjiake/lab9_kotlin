package com.example.lab9_1

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel: ViewModel() {
    lateinit var weatherList: MutableList<WeatherNW.DataWeather>
    private var weatherAPI = WeatherAPI.createAPI()
    init {
        Log.e("aaa","Init")
        getData(weatherList) { loadWeather() }
    }

    override fun onCleared() {
        Log.e("aaa","Cleared")
        super.onCleared()
    }

    private fun getData(weatherList: MutableList<WeatherNW.DataWeather>, callback: () -> Unit): MutableList<WeatherNW.DataWeather>{
        Log.e("aaa","getData")
        if(weatherList.isEmpty()){
            callback()
        }
        Log.e("aaa","return else: $weatherList")
        return weatherList
    }
    private fun loadWeather() {
        weatherAPI.getForecast(
            Constants.API_CITY,
            Constants.API_KEY,
            Constants.API_UNITS,
            Constants.API_LANG
        )
            .enqueue(object : Callback<WeatherNW> {
                override fun onResponse(call: Call<WeatherNW>, response: Response<WeatherNW>) {
                    if (response.isSuccessful) {
                        weatherList = (response.body()?.list as MutableList<WeatherNW.DataWeather>?)!!
                        Log.e("aaa","loadWeather: $weatherList")
                    }
                }
                override fun onFailure(call: Call<WeatherNW>, trowable: Throwable) {
                    Timber.tag(Constants.TIMBER_TAG).e(trowable)
                }
            })
    }
}