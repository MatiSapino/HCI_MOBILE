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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.ui.devices.ACCard
import com.example.mobileapp.ui.devices.Device
import com.example.mobileapp.ui.devices.DeviceSection
import com.example.mobileapp.ui.devices.DeviceTypeSection
import com.example.mobileapp.ui.devices.DeviceViewModel
import com.example.mobileapp.ui.devices.LightCard
import com.example.mobileapp.ui.devices.NewDeviceScreen
import com.example.mobileapp.ui.devices.Routine
import com.example.mobileapp.ui.devices.TapCard
import com.example.mobileapp.ui.devices.VacuumCard
import com.example.mobileapp.ui.routines.NewRoutineScreenState
import com.example.mobileapp.ui.routines.RoutineSection

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object LightCard : Screen("light_card")
    data object ACCard : Screen("ac_card")
    data object VacuumCard : Screen("vacuum_card")
    data object TapCard : Screen("tap_card")
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

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                devices = deviceViewModel.devices,
                routines = deviceViewModel.routines,
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
                    deviceViewModel.deleteRoutine(routine)
                }
            )
        }

        composable(Screen.LightCard.route) {
            val deviceId = navController.previousBackStackEntry?.arguments?.getString("deviceId")
            val device = deviceViewModel.devices.find { it.id == deviceId }
            device?.let {
                LightCard(
                    device = it,
                    onBack = { navController.navigate(Screen.HomeScreen.route) },
                    onDelete = { deletedDevice ->
                        deviceViewModel.deleteDevice(deletedDevice)
                        navController.navigate(Screen.HomeScreen.route)
                    },
                    onUpdateDevice = { updatedDevice ->
                        deviceViewModel.updateDevice(updatedDevice)
                    }
                )
            }
        }
        composable(Screen.ACCard.route) {
            val deviceId = navController.previousBackStackEntry?.arguments?.getString("deviceId")
            val device = deviceViewModel.devices.find { it.id == deviceId }
            device?.let {
                ACCard(
                    device = it,
                    onBack = { navController.navigate(Screen.HomeScreen.route) },
                    onDelete = { deletedDevice ->
                        deviceViewModel.deleteDevice(deletedDevice)
                        navController.navigate(Screen.HomeScreen.route)
                    },
                    onUpdateDevice = { updatedDevice ->
                        deviceViewModel.updateDevice(updatedDevice)
                    }
                )
            }
        }
        composable(Screen.VacuumCard.route) {
            val deviceId = navController.previousBackStackEntry?.arguments?.getString("deviceId")
            val device = deviceViewModel.devices.find { it.id == deviceId }
            device?.let {
                VacuumCard(
                    device = it,
                    onBack = { navController.navigate(Screen.HomeScreen.route) },
                    onDelete = { deletedDevice ->
                        deviceViewModel.deleteDevice(deletedDevice)
                        navController.navigate(Screen.HomeScreen.route)
                    },
                    onUpdateDevice = { updatedDevice ->
                        deviceViewModel.updateDevice(updatedDevice)
                    }
                )
            }
        }
        composable(Screen.TapCard.route) {
            val deviceId = navController.previousBackStackEntry?.arguments?.getString("deviceId")
            val device = deviceViewModel.devices.find { it.id == deviceId }
            device?.let {
                TapCard(
                    device = it,
                    onBack = { navController.navigate(Screen.HomeScreen.route) },
                    onDelete = { deletedDevice ->
                        deviceViewModel.deleteDevice(deletedDevice)
                        navController.navigate(Screen.HomeScreen.route)
                    },
                    onUpdateDevice = { updatedDevice ->
                        deviceViewModel.updateDevice(updatedDevice)
                    }
                )
            }
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
