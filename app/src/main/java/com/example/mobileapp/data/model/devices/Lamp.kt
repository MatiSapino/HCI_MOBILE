package com.example.mobileapp.data.model.devices

import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_devices.RemoteLamp
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteLampState

class Lamp(
    id: String?,
    name: String,
    val status: Status,
    val color: String,
    val brightness: Int
) : Device(id, name, DeviceType.LAMP, null) {

    override fun asRemoteModel(): RemoteDevice<RemoteLampState> {
        val state = RemoteLampState()
        state.status = Status.asRemoteModel(status)
        state.color = color
        state.brightness = brightness

        val model = RemoteLamp()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val TURN_ON = "turnOn"
        const val TURN_OFF = "turnOff"
        const val SET_COLOR = "setColor"
        const val SET_BRIGHTNESS = "setBrightness"
    }
}