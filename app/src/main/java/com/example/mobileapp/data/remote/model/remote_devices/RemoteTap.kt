package com.example.mobileapp.data.remote.model.remote_devices

import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Tap
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteTapState

class RemoteTap : RemoteDevice<RemoteTapState>() {

    override fun asModel(): Tap {
        return Tap(
            id = id,
            name = name,
            status = Status.stringToStatus(state.status)
        )
    }
}