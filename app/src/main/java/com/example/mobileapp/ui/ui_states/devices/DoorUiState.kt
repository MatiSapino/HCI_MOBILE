package com.example.mobileapp.ui.ui_states.devices

import com.example.mobileapp.data.model.devices.Door
import com.example.mobileapp.data.model.Error

data class DoorUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Door? = null
)

val DoorUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading