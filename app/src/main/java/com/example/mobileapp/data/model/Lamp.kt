package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.RemoteLamp
import com.example.mobileapp.data.remote.model.RemoteLampState

class Lamp(
    id: String?,
    name: String,
    val room: Room?,
    val status: Status,
    val color: String,
    val brightness: Int
) : Device(id, name, DeviceType.LAMP) {

    override fun asRemoteModel(): RemoteDevice<RemoteLampState> {
        val state = RemoteLampState()
        state.status = Status.asRemoteModel(status)
        state.color = color
        state.brightness = brightness

        val model = RemoteLamp()
        model.id = id
        model.name = name
        model.room = room?.asRemoteModel()
        model.setState(state)
        return model
    }

    companion object {
        const val TURN_ON_ACTION = "turnOn"
        const val TURN_OFF_ACTION = "turnOff"
    }
}