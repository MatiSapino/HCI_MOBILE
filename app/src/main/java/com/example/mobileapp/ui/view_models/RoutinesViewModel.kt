package com.example.mobileapp.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.DataSourceException
import com.example.mobileapp.data.model.Error
import com.example.mobileapp.data.repository.RoutineRepository
import com.example.mobileapp.ui.ui_states.RoutinesUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutinesViewModel (
    private val repository: RoutineRepository
    ) : ViewModel() {

        private val _uiState = MutableStateFlow(RoutinesUiState())
        val uiState = _uiState.asStateFlow()

        init {
            getRoutines()
        }
    
        fun getRoutines(){
            collectOnViewModelScope(
                repository.routines
            ) { state, response -> state.copy(routines = response) }
        }

        fun executeRoutine(routineId: String) {
            runOnViewModelScope(
                { repository.executeRoutine(routineId) },
                { state, response -> state}
            )
        }

        fun deleteRoutine(routineId: String) = viewModelScope.launch {
            runOnViewModelScope(
                { repository.deleteRoutine(routineId) },
                { state, _ -> state }
            ).invokeOnCompletion { getRoutines() }
        }

        private fun <T> collectOnViewModelScope(
            flow: Flow<T>,
            updateState: (RoutinesUiState, T) -> RoutinesUiState
        ) = viewModelScope.launch {
            flow
                .distinctUntilChanged()
                .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
                .collect { response -> _uiState.update { updateState(it, response) } }
        }

        private fun <R> runOnViewModelScope(
            block: suspend () -> R,
            updateState: (RoutinesUiState, R) -> RoutinesUiState
        ): Job = viewModelScope.launch {
            _uiState.update { it.copy(isFetching = true, error = null) }
            runCatching {
                block()
            }.onSuccess { response ->
                _uiState.update { updateState(it, response).copy(isFetching = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isFetching = false, error = handleError(e)) }
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