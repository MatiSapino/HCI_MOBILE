package com.example.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

class RemoteAction {
    @SerializedName("device")
    lateinit var device: RemoteRoutineDevice

    @SerializedName("actionName")
    lateinit var name: String

    @SerializedName("meta")
    var meta: Any? = null

    @SerializedName("params")
    var params: List<Any> = emptyList()
}