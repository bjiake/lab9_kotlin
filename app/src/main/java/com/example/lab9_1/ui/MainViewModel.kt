package com.example.lab9_1.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab9_1.App
import com.example.lab9_1.Constants
import com.example.lab9_1.data.toDomain
import com.example.lab9_1.data.toEntity
import com.example.lab9_1.domain.Weather
import com.example.lab9_1.domain.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList get() = _weatherList

    init {
        loadWeather()
    }

    private fun loadWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            _weatherList.postValue(repository.loadWeather())
        }
    }
}