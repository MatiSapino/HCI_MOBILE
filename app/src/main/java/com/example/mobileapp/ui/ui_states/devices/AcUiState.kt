package com.example.mobileapp.ui.ui_states.devices

import com.example.mobileapp.data.model.devices.Ac
import com.example.mobileapp.data.model.Error

data class AcUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Ac? = null
)

val AcUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading