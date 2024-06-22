package com.example.mobileapp.data.remote.model

import com.example.mobileapp.data.model.Routine
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class RemoteRoutine{
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("actions")
    @Expose(serialize = false)
    lateinit var actions: List<RemoteAction>

    @SerializedName("meta")
    var meta: Any? = null

    abstract fun asModel(): Routine
}