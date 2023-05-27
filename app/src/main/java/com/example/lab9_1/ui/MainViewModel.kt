package com.example.lab9_1.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab9_1.Event
import com.example.lab9_1.data.WhereGetWeatherData
import com.example.lab9_1.domain.model.Weather
import com.example.lab9_1.domain.WeatherRepository
import com.example.lab9_1.ui.Model.CityUI
import com.example.lab9_1.ui.Model.WeatherUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherList = MutableLiveData<List<WeatherUI>>()
    val weatherList get() = _weatherList
    private val _isFromDataBase = MutableLiveData<Event<Unit>>()
    val isFromDataBase get() = _isFromDataBase

    private val _city = MutableLiveData<CityUI>()
    val city: LiveData<CityUI> = _city
    private val _errorNetwork = MutableLiveData<Event<Unit>>()
    val errorNetwork: LiveData<Event<Unit>> = _errorNetwork


    fun loadWeather(lat: String, lon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (val weather = repository.loadWeather(lat, lon)) {
                    is WhereGetWeatherData.FromDataBase -> {
                        loadFromDataBase(weather)
                    }

                    is WhereGetWeatherData.FromNetWork -> {
                        loadFromNetWork(weather)
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.e("Error", e.toString())
            }
        }
    }

    private fun loadFromDataBase(weather: WhereGetWeatherData.FromDataBase) {
//        _weatherList.postValue(weather.weather.weatherList)
        _errorNetwork.postValue(Event(Unit))
        val weathers = weather.weather.weatherList.toUI()
        val city = weather.weather.city.toUI()
        _weatherList.postValue(weathers)
        _city.postValue(city)
    }

    private fun loadFromNetWork(weather: WhereGetWeatherData.FromNetWork) {
//        _weatherList.postValue(weather.weather.weatherList)
        _isFromDataBase.postValue(Event(Unit))
        val weathers = weather.weather.weatherList.toUI()
        val city = weather.weather.city.toUI()
        _weatherList.postValue(weathers)
        _city.postValue(city)
    }
}