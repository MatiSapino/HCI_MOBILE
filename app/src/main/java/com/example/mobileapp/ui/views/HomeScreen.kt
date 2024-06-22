package com.example.mobileapp.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Routine
import com.example.mobileapp.ui.components.DeviceSection
import com.example.mobileapp.ui.components.DeviceTypeSection
import com.example.mobileapp.ui.components.RoutineSection
import com.example.mobileapp.ui.view_models.DevicesViewModel
import com.example.mobileapp.ui.view_models.RoutinesViewModel
import com.example.mobileapp.ui.view_models.getViewModelFactory

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object LightCard : Screen("light_card")
    data object ACCard : Screen("ac_card")
    data object VacuumCard : Screen("vacuum_card")
    data object TapCard : Screen("tap_card")
    data object DoorCard : Screen("door_card")
    data object NewRoutineScreen : Screen("new_routine_screen")
    data object NewDeviceScreen : Screen("new_device_screen")
}


@Composable
fun HomeScreen(
    devicesVM: DevicesViewModel,
    routinesVM: RoutinesViewModel,
    onDeviceSelected: (Device) -> Unit,
    onAddRoutine: () -> Unit,
    onAddDevice: () -> Unit,
) {
    var routines: List<Routine> by remember { mutableStateOf(routinesVM.uiState.value.routines) }
    var devices: List<Device> by remember { mutableStateOf(devicesVM.uiState.value.devices) }
    var selectedDeviceType: DeviceType? = null

    fun filterDevices(selectedType: DeviceType?): List<Device>{
        if (selectedType == null) {
            return devices
        } else {
            return devices.filter { it.type == selectedType }
        }
    }

    devices.forEach({ Log.i("Device","Dispositivo de tipo: " +it.type.toString()+ " y de nombre: " +it.name) })


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
            .padding(16.dp)
    ) {
        DeviceTypeSection(
            deviceTypes = DeviceType.entries,
            selectedType = selectedDeviceType,
            onDeviceTypeSelected = {
                selectedDeviceType = it
            }
        )

        DeviceSection(devices = filterDevices(selectedDeviceType), onDeviceSelected = onDeviceSelected, onAddDevice = onAddDevice)

        RoutineSection(
            routines = routines,
            onAddRoutine = onAddRoutine,
            onDeleteRoutine = {routine -> routinesVM.deleteRoutine(routine.id!!)}
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MainScreen(onDeviceSelected: (Device) -> Unit,
               onAddRoutine: () -> Unit,
               onAddDevice: () -> Unit,) {
    val devicesVM: DevicesViewModel = viewModel(factory = getViewModelFactory())
    val routinesVM: RoutinesViewModel = viewModel(factory = getViewModelFactory())
    HomeScreen(
        devicesVM = devicesVM,
        routinesVM = routinesVM,
        onDeviceSelected = onDeviceSelected,
        onAddRoutine = onAddRoutine,
        onAddDevice = onAddDevice,
    )
}

