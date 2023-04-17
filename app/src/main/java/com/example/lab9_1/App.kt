package com.example.lab9_1

import android.app.Application
import com.example.lab9_1.data.WeatherRepositoryImpl
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.domain.WeatherRepository

class App : Application() {
    override fun onCreate() {
        val dataBase = WeatherDataBase.getDatabase(this)
        val api = WeatherAPI.createAPI()
        repository = WeatherRepositoryImpl(dataBase, api)
        super.onCreate()
    }

    companion object {
        lateinit var repository: WeatherRepository
            private set
    }

}