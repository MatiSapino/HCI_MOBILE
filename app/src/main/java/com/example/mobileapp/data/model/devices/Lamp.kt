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
    var status: Status?,
    var color: String?,
    var brightness: Int?
) : Device(id, name, DeviceType.LAMP, null) {

    @JvmName("setStatusInternal")
    fun setStatus(newStatus: Status) {
        status = newStatus
    }

    @JvmName("setColorInternal")
    fun setColor(newColor: String) {
        color = newColor
    }

    @JvmName("setBrightnessInternal")
    fun setBrightness(newBrightness: Int) {
        brightness = newBrightness
    }

    override fun asRemoteModel(): RemoteDevice<RemoteLampState> {
        val state = RemoteLampState()
        state.status = status.toString()
        state.color = color.toString()
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