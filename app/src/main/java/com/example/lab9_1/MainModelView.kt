package com.example.lab9_1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/*
а в самой ViewModel - получение данных из сети и их сохранение [2];
 */

class MainModelView : ViewModel(){
    var weatherList = emptyList<WeatherNW.DataWeather>()






}