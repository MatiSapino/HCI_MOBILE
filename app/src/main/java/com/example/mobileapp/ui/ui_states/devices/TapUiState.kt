package com.example.mobileapp.ui.ui_states.devices

import com.example.mobileapp.data.model.devices.Tap
import com.example.mobileapp.data.model.Error

data class TapUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Tap? = null
)

val TapUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading