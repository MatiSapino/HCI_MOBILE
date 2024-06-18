package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteRoom
import com.example.mobileapp.data.remote.model.RemoteRoomMeta

class Room(
    var id: String? = null,
    var name: String,
    var size: String,
    var color: String
) {

    fun asRemoteModel(): RemoteRoom {
        val meta = RemoteRoomMeta()
        meta.size = size
        meta.color = color

        val model = RemoteRoom()
        model.id = id
        model.name = name
        model.meta = meta

        return model
    }
}