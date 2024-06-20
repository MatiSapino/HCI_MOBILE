package com.example.mobileapp.data.remote.model.remote_devices

import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Door
import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.remote_device_state.RemoteDoorState

class RemoteDoor : RemoteDevice<RemoteDoorState>() {

    override fun asModel(): Door {
        return Door(
            id = id,
            name = name,
            status = Status.stringToStatus(state.status),
            lock = Status.stringToStatus(state.lock)
        )
    }
}