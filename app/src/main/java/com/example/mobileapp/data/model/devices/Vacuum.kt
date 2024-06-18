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
    val status: Status,
    val mode: String,
    val batteryLevel: Int,
    val location: Any?
) : Device(id, name, DeviceType.VACUUM, null) {

    override fun asRemoteModel(): RemoteDevice<RemoteVacuumState> {
        val state = RemoteVacuumState()
        state.status = Status.asRemoteModel(status)
        state.mode = mode
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
        const val SET_LOCATION = "setLocation"
    }
}