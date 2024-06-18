package com.example.mobileapp.data.remote.model

import com.example.mobileapp.data.model.Room
import com.google.gson.annotations.SerializedName

class RemoteRoom {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("meta")
    lateinit var meta: RemoteRoomMeta

    fun asModel() : Room {
        return Room(
            id = id,
            name = name,
            size = meta.size,
            color = meta.color
        )
    }
}