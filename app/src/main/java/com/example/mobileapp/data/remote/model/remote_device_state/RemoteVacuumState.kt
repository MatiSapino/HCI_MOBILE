package com.example.mobileapp.data.remote.model.remote_device_state

import com.google.gson.annotations.SerializedName

class RemoteVacuumState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("mode")
    lateinit var mode: String

    @SerializedName("batteryLevel")
    var batteryLevel: Int? = 0

    @SerializedName("location")
    var location: Any? = null
}