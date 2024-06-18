package com.example.mobileapp.data.model.devices

import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteTapState
import com.example.mobileapp.data.remote.model.remote_devices.RemoteTap

class Tap(
    id: String?,
    name: String,
    val status: Status,
) : Device(id, name, DeviceType.TAP, null) {

    override fun asRemoteModel(): RemoteDevice<RemoteTapState> {
        val state = RemoteTapState()
        state.status = Status.asRemoteModel(status)

        val model = RemoteTap()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val OPEN = "open"
        const val CLOSE = "close"
        const val DISPENSE = "dispense"
    }
}