package com.example.mobileapp.ui.view_models

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.mobileapp.ApiApplication
import com.example.mobileapp.data.repository.DeviceRepository
import com.example.mobileapp.data.repository.RoutineRepository
import com.example.mobileapp.ui.view_models.devices.AcViewModel
import com.example.mobileapp.ui.view_models.devices.DoorViewModel
import com.example.mobileapp.ui.view_models.devices.LampViewModel
import com.example.mobileapp.ui.view_models.devices.TapViewModel
import com.example.mobileapp.ui.view_models.devices.VacuumViewModel

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as ApiApplication)
    val routineRepository = application.routineRepository
    val deviceRepository = application.deviceRepository
    return ViewModelFactory(
        routineRepository,
        deviceRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}

class ViewModelFactory (
    private val routineRepository: RoutineRepository,
    private val deviceRepository: DeviceRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(RoutinesViewModel::class.java) ->
                RoutinesViewModel(routineRepository)

            isAssignableFrom(DevicesViewModel::class.java) ->
                DevicesViewModel(deviceRepository)

            isAssignableFrom(LampViewModel::class.java) ->
                LampViewModel(deviceRepository)

            isAssignableFrom(AcViewModel::class.java) ->
                AcViewModel(deviceRepository)

            isAssignableFrom(TapViewModel::class.java) ->
                TapViewModel(deviceRepository)

            isAssignableFrom(DoorViewModel::class.java) ->
                DoorViewModel(deviceRepository)

            isAssignableFrom(VacuumViewModel::class.java) ->
                VacuumViewModel(deviceRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}