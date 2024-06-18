package com.example.mobileapp

import android.app.Application
import com.example.mobileapp.data.remote.DeviceRemoteDataSource
import com.example.mobileapp.data.remote.RoomRemoteDataSource
import com.example.mobileapp.data.remote.api.RetrofitClient
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.data.repository.RoomRepository

class ApiApplication  : Application() {

    private val roomRemoteDataSource: RoomRemoteDataSource
        get() = RoomRemoteDataSource(RetrofitClient.roomService)

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    val roomRepository: RoomRepository
        get() = RoomRepository(roomRemoteDataSource)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)
}