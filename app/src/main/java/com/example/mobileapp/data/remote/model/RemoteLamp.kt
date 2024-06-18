package com.example.mobileapp.data.remote.model

import com.example.mobileapp.data.model.Lamp

class RemoteLamp : RemoteDevice<RemoteLampState>() {

    override fun asModel(): Lamp {
        return Lamp(
            id = id,
            name = name,
            room = room?.asModel(),
            status = RemoteStatus.asModel(state.status),
            color = state.color,
            brightness = state.brightness
        )
    }
}