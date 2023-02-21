package com.example.lab9_1

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.e("tasdf", response.body()?.string().orEmpty())
        return response
    }
}