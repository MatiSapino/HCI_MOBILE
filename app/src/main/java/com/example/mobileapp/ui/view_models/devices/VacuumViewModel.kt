package com.example.mobileapp.ui.view_models.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.DataSourceException
import com.example.mobileapp.data.model.Error
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.devices.Vacuum
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.ui.ui_states.devices.VacuumUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VacuumViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VacuumUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Vacuum?) }
        )
    }

    fun start() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Vacuum.START) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.ACTIVE)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun pause() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Vacuum.PAUSE) },
        { state, _ ->
            uiState.value.currentDevice?.setStatus(Status.INACTIVE)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun setMode(newMode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Vacuum.SET_MODE, arrayOf(newMode)) },
        { state, _ ->
            uiState.value.currentDevice?.setMode(newMode)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun dock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Vacuum.DOCK) },
        { state, _ -> state }
    )

    fun deleteDevice(deviceId: String?) = runOnViewModelScope(
        { repository.deleteDevice(deviceId) },
        { state, _ -> state.copy(currentDevice = null) }
    )


    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (VacuumUiState, R) -> VacuumUiState
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