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
    var status: Status,
    var lock: Status,
) : Device(id, name, DeviceType.DOOR, null) {

    fun setStatus(newStatus: Status) {
        status = newStatus
    }

    fun setLock(newLock: Status) {
        lock = newLock
    }

    override fun asRemoteModel(): RemoteDevice<RemoteDoorState> {
        val state = RemoteDoorState()
        state.status = status.toString()
        state.lock = lock.toString()

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