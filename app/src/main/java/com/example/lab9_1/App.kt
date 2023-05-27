package com.example.lab9_1

import android.app.Application
import androidx.room.Room
import com.example.lab9_1.data.WeatherRepositoryImpl
import com.example.lab9_1.data.local.WeatherDataBase
import com.example.lab9_1.data.network.WeatherAPI
import com.example.lab9_1.domain.WeatherRepository
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        initApi()
        initDatabase()
        initRepository()
        super.onCreate()
    }

    companion object {
        lateinit var weatherAPI: WeatherAPI
        lateinit var weatherDatabase: WeatherDataBase
        lateinit var repository: WeatherRepository
    }
    private fun initRepository() {
        repository = WeatherRepositoryImpl(weatherAPI, weatherDatabase)
    }

    private fun initApi() {
        weatherAPI = WeatherAPI.createAPI()
    }

    private fun initDatabase() {
        weatherDatabase = WeatherDataBase.getDatabase(this)
    }

}