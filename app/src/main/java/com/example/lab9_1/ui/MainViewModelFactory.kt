package com.example.lab9_1

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.example.lab9_1.data.WeatherRepositoryImpl
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.domain.WeatherRepository
import com.example.lab9_1.ui.MainViewModel

class MainViewModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("unknown view model class")
    }

}