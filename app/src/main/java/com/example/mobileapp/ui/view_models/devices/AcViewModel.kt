package com.example.mobileapp.ui.view_models.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.DataSourceException
import com.example.mobileapp.data.model.Error
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Ac
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.ui.ui_states.devices.AcUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AcViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AcUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Ac?) }
        )
    }

    fun turnOn() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.TURN_ON) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.ON)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun turnOff() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.TURN_OFF) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.OFF)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setMode(newMode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_MODE, arrayOf(newMode)) },
        { state, _ ->
            uiState.value.currentDevice?.setMode(newMode)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setTemperature(newTemperature: Int) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_TEMPERATURE, arrayOf(newTemperature)) },
        { state, _ ->
            uiState.value.currentDevice?.setTemperature(newTemperature)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setVerticalSwing(newVerticalSwing: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_VERTICAL_SWING, arrayOf(newVerticalSwing)) },
        { state, _ ->
            uiState.value.currentDevice?.setVerticalSwing(newVerticalSwing)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setHorizontalSwing(newHorizontalSwing: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_HORIZONTAL_SWING, arrayOf(newHorizontalSwing)) },
        { state, _ ->
            uiState.value.currentDevice?.setHorizontalSwing(newHorizontalSwing)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setFanSpeed(newFanSpeed: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Ac.SET_FAN_SPEED, arrayOf(newFanSpeed)) },
        { state, _ ->
            uiState.value.currentDevice?.setFanSpeed(newFanSpeed)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun deleteDevice(deviceId: String?) = runOnViewModelScope(
        { repository.deleteDevice(deviceId) },
        { state, _ -> state.copy(currentDevice = null) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (AcUiState, R) -> AcUiState
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