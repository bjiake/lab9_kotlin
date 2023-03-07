package com.example.lab9_1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab9_1.Constants.API_CITY
import com.example.lab9_1.Constants.API_KEY
import com.example.lab9_1.Constants.API_LANG
import com.example.lab9_1.Constants.API_UNITS
import com.example.lab9_1.Constants.TIMBER_TAG
import com.example.lab9_1.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private val weatherAdapter = WeatherAdapter()
    private lateinit var binding: ActivityMainBinding
    private var weatherAPI = WeatherAPI.createAPI()
    private lateinit var viewModel: MainViewModel
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        recycleViewInit()
        //ресурсы не эффективно используются, пару переворотов и зависает
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData {
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
}
