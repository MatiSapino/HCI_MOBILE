package com.example.mobileapp.ui.ui_states

import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.Error
data class DevicesUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val devices: List<Device> = emptyList()
)