package com.example.lab9_1

import MainViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.ui.MainViewModel

class MainViewModelFactory(var weatherAPI: WeatherAPI, var dataBase: WeatherDataBase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(weatherAPI, dataBase) as T
        }
        throw java.lang.IllegalArgumentException("unknown view model class")
    }

}