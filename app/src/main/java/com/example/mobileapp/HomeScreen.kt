package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Device(val name: String, val type: String)
data class Routine(val name: String, val automations: List<String>)

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object LightCard : Screen("light_card")
    data object ACCard : Screen("ac_card")
    data object VacuumCard : Screen("vacuum_card")
    data object TapCard : Screen("tap_card")
    data object NewRoutineScreen : Screen("new_routine_screen")
    data object NewDeviceScreen : Screen("new_device_screen")
}

class DeviceViewModel : ViewModel() {
    var devices by mutableStateOf(listOf(
        Device("Main Hall light", "Light"),
        Device("Vacuum cleaner", "Vacuum"),
        Device("Living room AC", "AC"),
        Device("Tap", "Tap")
    ))
        private set

    fun addDevice(device: Device) {
        devices = devices + device
    }

    fun deleteDevice(device: Device) {
        devices = devices.filter { it != device }
    }
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
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Types of Devices",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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
    var routines by remember {
        mutableStateOf(
            listOf(
                Routine("Morning", listOf("Set Color", "Set Brightness")),
                Routine("Out", listOf("Turn Off Light", "Close Tap"))
            )
        )
    }

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                devices = deviceViewModel.devices,
                routines = routines,
                onDeviceTypeSelected = { type -> selectedDeviceType = type },
                selectedDeviceType = selectedDeviceType,
                onDeviceSelected = { device ->
                    when (device.type) {
                        "Light" -> navController.navigate(Screen.LightCard.route)
                        "AC" -> navController.navigate(Screen.ACCard.route)
                        "Vacuum" -> navController.navigate(Screen.VacuumCard.route)
                        "Tap" -> navController.navigate(Screen.TapCard.route)
                    }
                },
                onRoutineSelected = {},
                onAddRoutine = { navController.navigate(Screen.NewRoutineScreen.route) },
                onAddDevice = { navController.navigate(Screen.NewDeviceScreen.route) },
                onDeleteRoutine = { routine ->
                    routines = routines.filter { it != routine }
                }
            )
        }

        composable(Screen.LightCard.route) {
            LightCard(
                onBack = { navController.navigate(Screen.HomeScreen.route) },
                onDelete = { device ->
                    deviceViewModel.deleteDevice(device)
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(Screen.ACCard.route) {
            ACCard(
                onBack = { navController.navigate(Screen.HomeScreen.route) },
                onDelete = { device ->
                    deviceViewModel.deleteDevice(device)
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(Screen.VacuumCard.route) {
            VacuumCard(
                onBack = { navController.navigate(Screen.HomeScreen.route) },
                onDelete = { device ->
                    deviceViewModel.deleteDevice(device)
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(Screen.TapCard.route) {
            TapCard(
                onBack = { navController.navigate(Screen.HomeScreen.route) },
                onDelete = { device ->
                    deviceViewModel.deleteDevice(device)
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }

        composable(Screen.NewRoutineScreen.route) {
            NewRoutineScreenState(
                onRoutineSaved = { navController.navigate(Screen.HomeScreen.route) },
                onCancel = { navController.navigate(Screen.HomeScreen.route) }
            )
        }
        composable(Screen.NewDeviceScreen.route) {
            NewDeviceScreen(
                onDeviceAdded = { addedDevice ->
                    deviceViewModel.addDevice(addedDevice)
                    navController.navigate(Screen.HomeScreen.route)
                },
                onCancel = { navController.navigate(Screen.HomeScreen.route) }
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
