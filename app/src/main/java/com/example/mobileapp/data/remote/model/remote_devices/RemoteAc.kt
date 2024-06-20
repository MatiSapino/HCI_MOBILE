package com.example.mobileapp.data.remote.model.remote_devices

import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Ac
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteAcState

class RemoteAc : RemoteDevice<RemoteAcState>() {

    override fun asModel(): Ac {
        return Ac(
            id = id,
            name = name,
            status = Status.stringToStatus(state.status),
            temperature = state.temperature,
            mode = state.mode,
            fanSpeed = state.fanSpeed,
            verticalSwing = state.verticalSwing,
            horizontalSwing = state.horizontalSwing
        )
    }
}