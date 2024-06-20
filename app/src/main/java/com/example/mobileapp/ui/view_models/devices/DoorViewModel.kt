package com.example.mobileapp.ui.view_models.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.DataSourceException
import com.example.mobileapp.data.model.Error
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Door
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.ui.ui_states.devices.DoorUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DoorViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoorUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Door?) }
        )
    }

    fun open() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.OPEN) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.OPENED)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun close() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.CLOSE) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.CLOSED)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun lock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.LOCK) },
        { state, _ ->
            uiState.value.currentDevice?.setLock(Status.LOCKED)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun unlock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.UNLOCK) },
        { state, _ ->
            uiState.value.currentDevice?.setLock(Status.UNLOCKED)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun deleteDevice(deviceId: String?) = runOnViewModelScope(
        { repository.deleteDevice(deviceId) },
        { state, _ -> state.copy(currentDevice = null) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (DoorUiState, R) -> DoorUiState
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