package com.example.mobileapp.data.remote.model

import com.example.mobileapp.data.model.Status

object RemoteStatus {
    const val ON = "on"
    const val OFF = "off"

    fun asModel(status: String): Status {
        return when (status) {
            ON -> Status.ON
            else -> Status.OFF
        }
    }
}