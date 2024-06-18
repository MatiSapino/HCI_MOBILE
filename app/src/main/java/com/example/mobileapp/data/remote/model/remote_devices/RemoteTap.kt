package com.example.mobileapp.data.remote.model.remote_devices

import com.example.mobileapp.data.model.devices.Tap
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteTapState
import com.example.mobileapp.data.remote.model.RemoteStatus

class RemoteTap : RemoteDevice<RemoteTapState>() {

    override fun asModel(): Tap {
        return Tap(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status)
        )
    }
}