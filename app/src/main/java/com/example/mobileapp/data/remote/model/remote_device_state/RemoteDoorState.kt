package com.example.mobileapp.data.remote.model.remote_device_state

import com.google.gson.annotations.SerializedName

class RemoteDoorState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("lock")
    lateinit var lock: String
}