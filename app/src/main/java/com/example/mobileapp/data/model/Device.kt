package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteDevice

abstract class Device(
    val id: String?,
    val name: String,
    val type: DeviceType
) {
    abstract fun asRemoteModel(): RemoteDevice<*>
}