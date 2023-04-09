package com.example.lab9_1

import android.app.Application
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherAPI

class App : Application() {
    override fun onCreate() {
        dataBase = WeatherDataBase.getDatabase(this)
        api = WeatherAPI.createAPI()
        super.onCreate()
    }

    companion object {
        lateinit var dataBase: WeatherDataBase
            private set
        lateinit var api: WeatherAPI
            private set
    }

}