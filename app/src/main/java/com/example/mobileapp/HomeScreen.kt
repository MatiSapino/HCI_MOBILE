package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Device(val name: String)
data class Routine(val name: String)

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object LightCard : Screen("light_card")
    data object ACCard : Screen("ac_card")
    data object VacuumCard : Screen("vacuum_card")
    data object TapCard : Screen("tap_card")
    data object NewRoutineScreen : Screen("new_routine_screen")
}

@Composable
fun DeviceTypeSection(onDeviceTypeSelected: (String) -> Unit) {
    val deviceTypes = listOf("Light", "AC", "Vacuum", "Tap")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        deviceTypes.forEach { type ->
            Button(onClick = { onDeviceTypeSelected(type) }) {
                Text(text = type)
                // Icon(imageVector = /* Icon for the device */, contentDescription = "$type Icon")
            }
        }
    }
}

@Composable
fun DeviceSection(devices: List<Device>, onDeviceSelected: (Device) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(devices) { device ->
            DeviceCard(device = device, onClick = { onDeviceSelected(device) })
        }
    }
}

@Composable
fun DeviceCard(device: Device, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = device.name)
            // Icon(imageVector = /* Icon for the device */, contentDescription = "${device.name} Icon")
        }
    }
}

@Composable
fun RoutineSection(routines: List<Routine>, onRoutineSelected: (Routine) -> Unit, onAddRoutine: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Routines", fontSize = 20.sp)
        IconButton(onClick = onAddRoutine) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Routine")
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(routines) { routine ->
            RoutineCard(routine = routine, onClick = { onRoutineSelected(routine) })
        }
    }
}

@Composable
fun RoutineCard(routine: Routine, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = routine.name)
        }
    }
}

@Composable
fun HomeScreen(
    devices: List<Device>,
    routines: List<Routine>,
    onDeviceTypeSelected: (String) -> Unit,
    onDeviceSelected: (Device) -> Unit,
    onRoutineSelected: (Routine) -> Unit,
    onAddRoutine: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp)
    ) {
        Text(
            text = "Types of Devices",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        DeviceTypeSection(onDeviceTypeSelected = onDeviceTypeSelected)

        Text(
            text = "Devices",
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        DeviceSection(devices = devices, onDeviceSelected = onDeviceSelected)

        RoutineSection(routines = routines, onRoutineSelected = onRoutineSelected, onAddRoutine = onAddRoutine)
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                devices = listOf(
                    Device("Main Hall light"),
                    Device("Vacuum cleaner"),
                    Device("Living room AC"),
                    Device("Tap")
                ),
                routines = listOf(
                    Routine("Morning"),
                    Routine("Out")
                ),
                onDeviceTypeSelected = {},
                onDeviceSelected = { device ->
                    when (device.name) {
                        "Main Hall light" -> navController.navigate(Screen.LightCard.route)
                        "Living room AC" -> navController.navigate(Screen.ACCard.route)
                        "Vacuum cleaner" -> navController.navigate(Screen.VacuumCard.route)
                        "Tap" -> navController.navigate(Screen.TapCard.route)
                    }
                },
                onRoutineSelected = {},
                onAddRoutine = { navController.navigate(Screen.NewRoutineScreen.route) }
            )
        }
        composable(Screen.LightCard.route) { LightCard() }
        composable(Screen.ACCard.route) { ACCard() }
        composable(Screen.VacuumCard.route) { VacuumCard() }
        composable(Screen.TapCard.route) { TapCard() }
        composable(Screen.NewRoutineScreen.route) { NewRoutineScreenState(onRoutineSaved = { navController.navigate(Screen.HomeScreen.route) }) }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
