package com.example.mobileapp.data.model.devices

import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteDoorState
import com.example.mobileapp.data.remote.model.remote_devices.RemoteDoor

class Door(
    id: String?,
    name: String,
    val status: Status,
    val lock: String,
) : Device(id, name, DeviceType.DOOR, null) {

    override fun asRemoteModel(): RemoteDevice<RemoteDoorState> {
        val state = RemoteDoorState()
        state.status = Status.asRemoteModel(status)
        state.lock = lock

        val model = RemoteDoor()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val OPEN = "open"
        const val CLOSE = "close"
        const val LOCK = "lock"
        const val UNLOCK = "unlock"
    }
}