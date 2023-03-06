package com.example.lab9_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//class ModelFactory constructor(private val weatherList: LiveData<List<WeatherNW.DataWeather>>): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if(modelClass.isAssignableFrom(MainModelView::class.java)){
//            MainModelView(this.weatherList) as T
//        }
//        else{
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//}