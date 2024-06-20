package com.example.mobileapp.ui.views

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Routine
import com.example.mobileapp.ui.components.DeviceSection
import com.example.mobileapp.ui.components.DeviceTypeSection
import com.example.mobileapp.ui.components.RoutineSection

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
    devices: List<Device>,
    routines: List<Routine>,
    onDeviceTypeSelected: (String?) -> Unit,
    selectedDeviceType: String?,
    onDeviceSelected: (Device) -> Unit,
    onRoutineSelected: (Routine) -> Unit,
    onAddRoutine: () -> Unit,
    onAddDevice: () -> Unit,
    onDeleteRoutine: (Routine) -> Unit
) {
    val filteredDevices = if (selectedDeviceType == null) {
        devices
    } else {
        devices.filter { it.type == selectedDeviceType }
    }

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
            deviceTypes = listOf("Light", "AC", "Vacuum", "Tap"),
            selectedType = selectedDeviceType,
            onDeviceTypeSelected = onDeviceTypeSelected
        )

        DeviceSection(devices = filteredDevices, onDeviceSelected = onDeviceSelected, onAddDevice = onAddDevice)

        RoutineSection(
            routines = routines,
            onRoutineSelected = onRoutineSelected,
            onAddRoutine = onAddRoutine,
            onDeleteRoutine = onDeleteRoutine
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedDeviceType by remember { mutableStateOf<String?>(null) }
    val deviceViewModel: DeviceViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                devices = deviceViewModel.devices,
                routines = deviceViewModel.routines,
                onDeviceTypeSelected = { type -> selectedDeviceType = type },
                selectedDeviceType = selectedDeviceType,
                onDeviceSelected = { device ->
                    when (device.type) {
                        DeviceType.LAMP -> navController.navigate(Screen.LightCard.route + "/" +device.id)
                        DeviceType.AC -> navController.navigate(Screen.ACCard.route + "/" +device.id)
                        DeviceType.VACUUM -> navController.navigate(Screen.VacuumCard.route + "/" +device.id)
                        DeviceType.TAP -> navController.navigate(Screen.TapCard.route + "/" +device.id)
                        DeviceType.DOOR -> navController.navigate(Screen.DoorCard.route + "/" +device.id)
                    }
                },
                onRoutineSelected = {},
                onAddRoutine = { navController.navigate(Screen.NewRoutineScreen.route) },
                onAddDevice = { navController.navigate(Screen.NewDeviceScreen.route) },
                onDeleteRoutine = { routine ->
                    deviceViewModel.deleteRoutine(routine)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
