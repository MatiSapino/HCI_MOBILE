package com.example.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

class RemoteAction<T> where T : Any {
    @SerializedName("device")
    lateinit var device: RemoteDevice<*>

    @SerializedName("actionName")
    lateinit var name: String

    @SerializedName("meta")
    var meta: Any? = null

    @SerializedName("params")
    var params: List<*>? = null
}