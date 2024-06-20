package com.example.mobileapp.data.model.devices

import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_devices.RemoteAc
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteAcState

class Ac(
    id: String?,
    name: String,
    var status: Status,
    var temperature: Int,
    var mode: String,
    var verticalSwing: String,
    var horizontalSwing: String,
    var fanSpeed: String,
) : Device(id, name, DeviceType.AC, null) {

    @JvmName("setStatusInternal")
    fun setStatus(newStatus: Status) {
        status = newStatus
    }

    @JvmName("setTemperatureInternal")
    fun setTemperature(newTemperature: Int) {
        temperature = newTemperature
    }

    @JvmName("setModeInternal")
    fun setMode(newMode: String) {
        mode = newMode
    }

    @JvmName("setVSwingInternal")
    fun setVerticalSwing(newVerticalSwing: String) {
        verticalSwing = newVerticalSwing
    }
    @JvmName("setHSwingInternal")
    fun setHorizontalSwing(newHorizontalSwing: String) {
        horizontalSwing = newHorizontalSwing
    }

    @JvmName("setSpeedInternal")
    fun setFanSpeed(newFanSpeed: String) {
        fanSpeed = newFanSpeed
    }

    override fun asRemoteModel(): RemoteDevice<RemoteAcState> {
        val state = RemoteAcState()
        state.status = status.toString()
        state.temperature = temperature
        state.mode = mode
        state.verticalSwing = verticalSwing
        state.horizontalSwing = horizontalSwing
        state.fanSpeed = fanSpeed

        val model = RemoteAc()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val TURN_ON = "turnOn"
        const val TURN_OFF = "turnOff"
        const val SET_TEMPERATURE = "setTemperature"
        const val SET_VERTICAL_SWING = "setVerticalSwing"
        const val SET_HORIZONTAL_SWING = "setHorizontalSwing"
        const val SET_FAN_SPEED = "setFanSpeed"
        const val SET_MODE = "setMode"
    }
}