package com.example.mobileapp.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Device(val id: String, val name: String, val type: String, val state: MutableMap<String, Any>)
data class Routine(val id: String, val name: String, val icon: Int, val automations: List<String>)

sealed class Automation(open val deviceId: String, open val action: String)
data class LightAutomation(override val deviceId: String, override val action: String, val value: Any) : Automation(deviceId, action)
// Define other types of automations for AC, Vacuum, Tap similarly

class DeviceViewModel : ViewModel() {
    var devices by mutableStateOf(listOf<Device>())
        private set

    var routines by mutableStateOf(listOf<Routine>())
        private set

    fun addDevice(device: Device) {
        devices = devices + device
    }

    fun updateDevice(device: Device) {
        devices = devices.map { if (it.id == device.id) device else it }
    }

    fun deleteDevice(device: Device) {
        devices = devices.filter { it.id != device.id }
    }

    fun addRoutine(routine: Routine) {
        routines = routines + routine
    }

    fun deleteRoutine(routine: Routine) {
        routines = routines.filter { it != routine }
    }
}
