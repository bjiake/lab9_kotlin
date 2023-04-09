package com.example.lab9_1.data.network

import com.example.lab9_1.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Todo
interface WeatherAPI {
    companion object {
        fun createAPI(): WeatherAPI {
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitBuilder.create(WeatherAPI::class.java)
        }
    }

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") key: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): Response<WeatherNW>
}