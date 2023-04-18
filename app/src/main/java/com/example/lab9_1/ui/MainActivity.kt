package com.example.lab9_1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab9_1.*
import com.example.lab9_1.databinding.ActivityMainBinding
import com.example.lab9_1.domain.Weather
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private val weatherAdapter = WeatherAdapter(
        onLongClick = {
            Log.d("aaa", "$it")
            shareWeather(it)
            }
    )
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recycleViewInit()

        viewModelFactory = MainViewModelFactory(App.repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.weatherList.observe(this) {
            weatherAdapter.submitList(it)
        }
    }

    private fun recycleViewInit() {
        binding.rvWeather.adapter = weatherAdapter
        binding.rvWeather.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun shareWeather(weather: Weather){
        val sendIntent = Intent().apply {
            val weatherTimeDate = weather.dtTxt.split(" ")

            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${Constants.API_CITY} ${weather.dtTxt} ${weather.temperature}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Send text")
        startActivity(shareIntent)
    }
}