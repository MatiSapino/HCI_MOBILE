package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteDeviceType

abstract class ConcreteDeviceType(
    val id: String,
    val name: String,
    val powerUsage: Int?,
    ) {
        abstract fun asRemoteModel(): RemoteDeviceType
}