package com.example.mobileapp.ui.ui_states

import com.example.mobileapp.data.model.Routine
import com.example.mobileapp.data.model.Error
data class RoutinesUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val routines: List<Routine> = emptyList()
)