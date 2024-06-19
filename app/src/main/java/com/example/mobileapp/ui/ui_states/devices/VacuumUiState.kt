package com.example.mobileapp.ui.ui_states.devices

import com.example.mobileapp.data.model.devices.Vacuum
import com.example.mobileapp.data.model.Error

data class VacuumUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Vacuum? = null
)

val VacuumUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading