package com.example.mobileapp.data.remote.model

import com.example.mobileapp.data.model.ConcreteDeviceType
import com.google.gson.annotations.SerializedName

abstract class RemoteDeviceType {
    @SerializedName("id")
    lateinit var id: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("powerUsage")
    var powerUsage: Int? = null

    abstract fun asModel(): ConcreteDeviceType



    companion object {
        const val LAMP_TYPE_ID = "go46xmbqeomjrsjr"
        const val TAP_TYPE_ID = "dbrlsh7o5sn8ur4i"
        const val AC_TYPE_ID = "li6cbv5sdlatti0j"
        const val DOOR_TYPE_ID = "lsf78ly0eqrjbz91"
        const val VACUUM_TYPE_ID = "ofglvd9gqx8yfl3l"
    }
}