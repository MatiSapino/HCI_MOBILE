package com.example.mobileapp.data.remote.api

import com.example.mobileapp.data.remote.api.adapters.DateTypeAdapter
import com.example.mobileapp.data.remote.api.adapters.DeviceTypeAdapter
import com.example.mobileapp.data.remote.api.adapters.RoutineTypeAdapter
import com.example.mobileapp.data.remote.api.services.DeviceService
import com.example.mobileapp.data.remote.api.services.RoutineService
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.RemoteRoutine
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

private const val API_BASE_URL: String = "http://10.0.2.2:8080/api/"

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

private val gson = GsonBuilder()
    .registerTypeAdapter(Date::class.java, DateTypeAdapter())
    .registerTypeAdapter(RemoteDevice::class.java, DeviceTypeAdapter())
    .registerTypeAdapter(RemoteRoutine::class.java, RoutineTypeAdapter())
    .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()

object RetrofitClient {
    val routineService: RoutineService by lazy {
        retrofit.create(RoutineService::class.java)
    }

    val deviceService : DeviceService by lazy {
        retrofit.create(DeviceService::class.java)
    }
}