package com.example.lab9_1

import MainViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.ui.MainViewModel

class MainViewModelFactory(var weatherAPI: WeatherAPI) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(weatherAPI) as T
        }
        throw java.lang.IllegalArgumentException("unknown view model class")
    }

}