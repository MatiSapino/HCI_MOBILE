package com.example.mobileapp.ui.ui_states.devices

import com.example.mobileapp.data.model.devices.Lamp
import com.example.mobileapp.data.model.Error

data class LampUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Lamp? = null
)

val LampUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading