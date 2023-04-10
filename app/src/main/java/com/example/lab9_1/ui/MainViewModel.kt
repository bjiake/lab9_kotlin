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

class MainViewModel : ViewModel() {
    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList get() = _weatherList

    init {
        loadWeather()
    }

    private fun loadWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = App.api.getForecast(
                Constants.API_CITY,
                Constants.API_KEY,
                Constants.API_UNITS,
                Constants.API_LANG
            )
            try {
                if(response.isSuccessful){
                    val weather = response.body()?.list?.map {
                        it.toDomain()
                    }
                    _weatherList.postValue(weather)

                    weather?.forEach {
                        val date = it.dtTxt.split(" ")[0]
                        val count = App.dataBase.weatherDao().getWeatherCountByDate(date)

                        if (count == 0) {
                            App.dataBase.weatherDao().saveWeather(it.toEntity())
                        }
                    }
                }
                else{
                    _weatherList.postValue(listOf(App.dataBase.weatherDao().getAllWeather().toDomain()))
                }
            } catch (e: HttpException) {
                Log.e("aaa", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.d("ooops", "Something else went wrong: $e")
            }
        }
    }
}