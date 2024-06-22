package com.example.mobileapp.data.model.devices

import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteVacuumState
import com.example.mobileapp.data.remote.model.remote_devices.RemoteVacuum

class Vacuum(
    id: String?,
    name: String,
    var status: Status?,
    var mode: String?,
    var batteryLevel: Int?,
    var location: Any?
) : Device(id, name, DeviceType.VACUUM, null) {

    @JvmName("setStatusInternal")
    fun setStatus(newStatus: Status) {
        status = newStatus
    }

    @JvmName("setModeInternal")
    fun setMode(newMode: String) {
        mode = newMode
    }

    @JvmName("setBatteryInternal")
    fun setBatteryLevel(newBatteryLevel: Int) {
        batteryLevel = newBatteryLevel
    }

    @JvmName("setLocationInternal")
    fun setLocation(newLocation: Any?) {
        location = newLocation
    }

    override fun asRemoteModel(): RemoteDevice<RemoteVacuumState> {
        val state = RemoteVacuumState()
        state.status = status.toString()
        state.mode = mode.toString()
        state.batteryLevel = batteryLevel
        state.location = location


        val model = RemoteVacuum()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val START = "start"
        const val PAUSE = "pause"
        const val DOCK = "dock"
        const val SET_MODE = "setMode"
    }
}