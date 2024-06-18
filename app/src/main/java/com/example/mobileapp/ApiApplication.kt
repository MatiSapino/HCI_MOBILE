package com.example.mobileapp

import android.app.Application
import com.example.mobileapp.data.remote.data_sources.DeviceRemoteDataSource
import com.example.mobileapp.data.remote.data_sources.RoutineRemoteDataSource
import com.example.mobileapp.data.remote.api.RetrofitClient
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.data.repository.RoutineRepository

class ApiApplication  : Application() {

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    private val routineRemoteDataSource: RoutineRemoteDataSource
        get() = RoutineRemoteDataSource(RetrofitClient.routineService)


    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)

    val routineRepository: RoutineRepository
        get() = RoutineRepository(routineRemoteDataSource)
}