package com.example.lab9_1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MainViewModel : ViewModel() {
    var weatherList = MutableLiveData<List<WeatherNW.DataWeather>>()
    private var weatherAPI = WeatherAPI.createAPI()

    init {
        loadWeather()
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
                        Log.d("Tag","loadw")
                        weatherList.value = response.body()?.list
                    }
                }
                override fun onFailure(call: Call<WeatherNW>, trowable: Throwable) {
                    Timber.tag(Constants.TIMBER_TAG).e(trowable)
                }
            })
    }
}