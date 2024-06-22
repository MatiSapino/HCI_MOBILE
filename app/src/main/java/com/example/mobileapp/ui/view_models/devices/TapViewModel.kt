package com.example.mobileapp.ui.view_models.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.DataSourceException
import com.example.mobileapp.data.model.Error
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.data.model.Unit
import com.example.mobileapp.data.model.devices.Tap
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.ui.ui_states.devices.TapUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TapViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TapUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Tap?) }
        )
    }

    fun open() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Tap.OPEN) },
        { state, _ ->
            uiState.value.currentDevice?.setNewStatus(Status.OPENED)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )

    fun close() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Tap.CLOSE) },
        { state, _ ->
            uiState.value.currentDevice?.setNewStatus(Status.CLOSED)
            state.copy(currentDevice =  uiState.value.currentDevice)
        }
    )


    fun dispense(quantity: Int, unit: Unit) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Tap.DISPENSE, arrayOf(quantity, unit.toString())) },
        { state, _ -> state }
    )

    fun deleteDevice(deviceId: String?) = runOnViewModelScope(
        { repository.deleteDevice(deviceId) },
        { state, _ -> state.copy(currentDevice = null) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (TapUiState, R) -> TapUiState
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