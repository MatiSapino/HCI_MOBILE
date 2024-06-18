package com.example.mobileapp.data.remote.model

import com.example.mobileapp.data.model.Routine
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class RemoteRoutine<T> where T : Any {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("actions")
    @Expose(serialize = false)
    lateinit var actions: List<RemoteAction<*>>

    @SerializedName("meta")
    var meta: Any? = null

    @Expose(serialize = false)
    lateinit var state: T
        private set

    fun setState(state: T) {
        this.state = state
    }

    abstract fun asModel(): Routine
}