package com.example.mobileapp.ui.view_models.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.DataSourceException
import com.example.mobileapp.data.model.Error
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Lamp
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.ui.ui_states.devices.LampUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LampViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LampUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Lamp?) }
        )
    }

    fun turnOn() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Lamp.TURN_ON) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.ON)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun turnOff() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Lamp.TURN_OFF) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.OFF)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setColor(newColor: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Lamp.SET_COLOR, arrayOf(newColor)) },
        { state, _ ->
            uiState.value.currentDevice?.setColor(newColor)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setBrightness(newBrightness: Int) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Lamp.SET_BRIGHTNESS, arrayOf(newBrightness)) },
        { state, _ ->
            uiState.value.currentDevice?.setBrightness(newBrightness)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun deleteDevice(deviceId: String?) = runOnViewModelScope(
        { repository.deleteDevice(deviceId) },
        { state, _ -> state.copy(currentDevice = null) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (LampUiState, R) -> LampUiState
    ): Job = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { updateState(it, response).copy(loading = false) }
        }.onFailure { e ->
            _uiState.update { it.copy(loading = false, error = handleError(e)) }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}