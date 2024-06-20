package com.example.mobileapp.data.remote.model.remote_devices

import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Lamp
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteLampState
import com.example.mobileapp.data.remote.model.RemoteStatus

class RemoteLamp : RemoteDevice<RemoteLampState>() {

    override fun asModel(): Lamp {
        return Lamp(
            id = id,
            name = name,
            status = Status.stringToStatus(state.status),
            color = state.color,
            brightness = state.brightness
        )
    }
}