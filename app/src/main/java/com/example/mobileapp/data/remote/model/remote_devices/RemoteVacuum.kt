package com.example.mobileapp.data.remote.model.remote_devices

import com.example.mobileapp.data.model.devices.Vacuum
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.RemoteStatus
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteVacuumState

class RemoteVacuum : RemoteDevice<RemoteVacuumState>() {

    override fun asModel(): Vacuum {
        return Vacuum(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            mode = state.mode,
            batteryLevel = state.batteryLevel,
            location = state.location
        )
    }
}