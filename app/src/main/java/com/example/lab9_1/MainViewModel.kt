package com.example.lab9_1

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : ViewModel() {
    var weatherList = mutableListOf<WeatherNW.DataWeather>()
    private var weatherAPI = WeatherAPI.createAPI()

    fun getData(callback: (List<WeatherNW.DataWeather>) -> Unit) {
        if (weatherList.isEmpty()) {
            Log.d("оTAG", "Пошли грузить погоду")
            loadWeather(callback)
        } else {
            callback(weatherList)
        }
    }

    private fun loadWeather(callback: (List<WeatherNW.DataWeather>) -> Unit) {
        weatherAPI.getForecast(
            Constants.API_CITY,
            Constants.API_KEY,
            Constants.API_UNITS,
            Constants.API_LANG
        )
            .enqueue(object : Callback<WeatherNW> {
                override fun onResponse(call: Call<WeatherNW>, response: Response<WeatherNW>) {
                    if (response.isSuccessful) {
                        weatherList = response.body()?.list?.toMutableList()!!
                        callback(weatherList)
                    }
                }

                override fun onFailure(call: Call<WeatherNW>, trowable: Throwable) {
                    Timber.tag(Constants.TIMBER_TAG).e(trowable)
                }
            })
    }
}