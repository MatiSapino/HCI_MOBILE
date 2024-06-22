package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteDeviceType
import com.example.mobileapp.data.remote.model.RemoteRoutineDevice


class RoutineDevice (
        var id: String,
        var name: String,
        var type: ConcreteDeviceType
) {
        fun asRemoteModel(): RemoteRoutineDevice {
                return RemoteRoutineDevice(id, name, type.asRemoteModel())
        }
}
