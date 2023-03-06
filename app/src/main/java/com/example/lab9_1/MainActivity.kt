package com.example.lab9_1

import android.R.id
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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
    private lateinit var fragment: WeatherFragment
    private val weatherAdapter = WeatherAdapter()

    //private var weatherList = listOf<WeatherNW.DataWeather>()
    private lateinit var binding: ActivityMainBinding
    private var weatherAPI = WeatherAPI.createAPI()
    private lateinit var viewModel: MainModelView
    /*
    Выполните рефактор приложения таким образом, чтобы в MainActivity были
    только методы получения данных из ViewModel и их визуализации на экране,
    а в самой ViewModel - получение данных из сети и их сохранение [2];
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //gut asdf kak
//        fragment =
//            supportFragmentManager.findFragmentByTag(WeatherFragment.TAG) as WeatherFragment?
//            ?: WeatherFragment().apply {
//                supportFragmentManager
//                    .beginTransaction()
//                    .add(this, WeatherFragment.TAG)
//                    .commit()
//            }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("aaa","activity created")

        viewModel = ViewModelProvider(this).get(MainModelView::class.java)

        recycleViewInit()
        //Забрать данные и визуализация
        //fragment.weatherList = viewModel.getData(fragment)
        weatherAdapter.submitList(viewModel.weatherList)
/*
        if (fragment.weatherList.isEmpty()) {
            //loadWeather()
            viewModel.getData()

            Log.d("TAGGG", "Залез в сеть")
        } else {
            weatherAdapter.submitList(fragment.weatherList)
            Log.d("TAGGG", "Восстановил из WeatherStore")
        }
 */
    }
    private fun recycleViewInit() {
        binding.rvWeather.adapter = weatherAdapter

        binding.rvWeather.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun loadWeather() {
        weatherAPI.getForecast(API_CITY, API_KEY, API_UNITS, API_LANG)
            .enqueue(object : Callback<WeatherNW> {
                override fun onResponse(call: Call<WeatherNW>, response: Response<WeatherNW>) {
                    if (response.isSuccessful) {
                        fragment.weatherList = response.body()?.list!!
                        weatherAdapter.submitList(fragment.weatherList)
                    }
                }

                override fun onFailure(call: Call<WeatherNW>, trowable: Throwable) {
                    Timber.tag(TIMBER_TAG).e(trowable)
                }
            })
    }
}
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        val jsonWeather = Gson().toJson(weatherList)
//        outState.putString(STATE_WEATHER, jsonWeather)
//        Timber.tag(TIMBER_TAG).d("Сохранил")
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        val type = object : TypeToken<List<WeatherNW.DataWeather>>() {}.type
//        weatherList = Gson().fromJson(savedInstanceState.getString(STATE_WEATHER), type)
//        Timber.tag(TIMBER_TAG).d("Восстановил")
//        weatherAdapter.submitList(weatherList)
//    }