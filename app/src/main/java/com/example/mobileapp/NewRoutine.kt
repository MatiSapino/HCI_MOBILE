package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val selectedColor = Color.Gray
private val unselectedColor = Color.Transparent

@Composable
fun RoutineNameInput(name: String, onNameChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Routine Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun IconSelection(selectedIcon: Int?, onIconSelected: (Int) -> Unit) {
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.ShoppingCart,
        Icons.Default.Phone,
        Icons.Default.Build
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(icons.size) { index ->
            Icon(
                imageVector = icons[index],
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clickable { onIconSelected(index) }
                    .background(if (selectedIcon == index) selectedColor else unselectedColor)
            )
        }
    }
}

@Composable
fun DeviceTypeSelection(deviceTypes: List<String>, selectedType: String?, onTypeSelected: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(deviceTypes.size) { index ->
            val type = deviceTypes[index]
            Button(
                onClick = { onTypeSelected(type) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (type == selectedType) selectedColor else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = type)
            }
        }
    }
}

@Composable
fun DeviceSelection(devices: List<Device>, selectedDevice: Device?, onDeviceSelected: (Device) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(devices) { device ->
            Text(
                text = device.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDeviceSelected(device) }
                    .background(if (selectedDevice == device) selectedColor else unselectedColor)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun AutomationSelection(
    device: Device?,
    automations: List<String>,
    selectedAutomations: List<String>,
    onAutomationSelected: (String) -> Unit,
    onAcceptAutomation: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        if (device != null) {
            Text(text = "Select Automation for ${device.name}")
            automations.forEach { automation ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAutomationSelected(automation) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedAutomations.contains(automation),
                        onCheckedChange = { onAutomationSelected(automation) }
                    )
                    Text(text = automation)
                }
            }
            Button(onClick = onAcceptAutomation, modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)) {
                Text("Accept Automation")
            }
        }
    }
}

@Composable
fun SelectedActions(icon: Int?, actions: List<String>, onSaveRoutine: () -> Unit) {
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.ShoppingCart,
        Icons.Default.Phone,
        Icons.Default.Build
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        icon?.let {
            Icon(imageVector = icons[it], contentDescription = null, modifier = Modifier.size(64.dp))
        }
        actions.forEach { action ->
            Text(text = action, modifier = Modifier.padding(vertical = 4.dp))
        }
        Button(onClick = onSaveRoutine, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 16.dp)) {
            Text("Save Routine")
        }
    }
}

@Composable
fun NewRoutineScreen(
    routineName: String,
    onNameChange: (String) -> Unit,
    selectedIcon: Int?,
    onIconSelected: (Int) -> Unit,
    deviceTypes: List<String>,
    selectedType: String?,
    onTypeSelected: (String) -> Unit,
    devices: List<Device>,
    selectedDevice: Device?,
    onDeviceSelected: (Device) -> Unit,
    automations: List<String>,
    selectedAutomations: List<String>,
    onAutomationSelected: (String) -> Unit,
    onAcceptAutomation: () -> Unit,
    onSaveRoutine: () -> Unit,
    onRoutineSaved: () -> Unit // Callback to manage the action of saving the routine
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp)) {
        RoutineNameInput(name = routineName, onNameChange = onNameChange)
        IconSelection(selectedIcon = selectedIcon, onIconSelected = onIconSelected)
        DeviceTypeSelection(deviceTypes = deviceTypes, selectedType = selectedType, onTypeSelected = onTypeSelected)
        DeviceSelection(devices = devices, selectedDevice = selectedDevice, onDeviceSelected = onDeviceSelected)
        AutomationSelection(
            device = selectedDevice,
            automations = automations,
            selectedAutomations = selectedAutomations,
            onAutomationSelected = onAutomationSelected,
            onAcceptAutomation = onAcceptAutomation
        )
        SelectedActions(icon = selectedIcon, actions = selectedAutomations, onSaveRoutine = {
            onSaveRoutine()
            onRoutineSaved() // Clear all fields and go back to home screen
        })
    }
}

@Composable
fun NewRoutineScreenState(onRoutineSaved: () -> Unit) {
    var routineName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf<Int?>(null) }
    var selectedType by remember { mutableStateOf<String?>(null) }
    var selectedDevice by remember { mutableStateOf<Device?>(null) }
    var selectedAutomations by remember { mutableStateOf<List<String>>(emptyList()) }

    NewRoutineScreen(
        routineName = routineName,
        onNameChange = { routineName = it },
        selectedIcon = selectedIcon,
        onIconSelected = { selectedIcon = it },
        deviceTypes = listOf("Light", "AC", "Vacuum", "Tap"),
        selectedType = selectedType,
        onTypeSelected = { selectedType = it },
        devices = listOf(Device("Device 1"), Device("Device 2")),
        selectedDevice = selectedDevice,
        onDeviceSelected = { selectedDevice = it },
        automations = listOf("Set Color", "Set Brightness"),
        selectedAutomations = selectedAutomations,
        onAutomationSelected = { automation ->
            selectedAutomations = if (selectedAutomations.contains(automation)) {
                selectedAutomations - automation
            } else {
                selectedAutomations + automation
            }
        },
        onAcceptAutomation = {},
        onSaveRoutine = {
            // Logic to save routine
            // Call to the Api
        },
        onRoutineSaved = {
            routineName = ""
            selectedIcon = null
            selectedType = null
            selectedDevice = null
            selectedAutomations = emptyList()
            onRoutineSaved() // Go back to home screen
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewRoutineScreenPreview() {
    NewRoutineScreenState(onRoutineSaved = {})
}
