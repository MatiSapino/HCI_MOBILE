package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.UUID

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
    onRoutineSaved: () -> Unit, // Callback to manage the action of saving the routine
    onCancel: () -> Unit // Callback to handle cancel action
) {
    val filteredDevices = if (selectedType != null) {
        devices.filter { it.type == selectedType }
    } else {
        devices
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp)) {
        IconButton(onClick = onCancel, modifier = Modifier.align(Alignment.Start)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        RoutineNameInput(name = routineName, onNameChange = onNameChange)
        IconSelection(selectedIcon = selectedIcon, onIconSelected = onIconSelected)
        DeviceTypeSelection(deviceTypes = deviceTypes, selectedType = selectedType, onTypeSelected = onTypeSelected)
        DeviceSelection(devices = filteredDevices, selectedDevice = selectedDevice, onDeviceSelected = onDeviceSelected)
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
fun NewRoutineScreenState(onRoutineSaved: () -> Unit, onCancel: () -> Unit) {
    val deviceViewModel: DeviceViewModel = viewModel()
    var routineName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf<Int?>(null) }
    var selectedType by remember { mutableStateOf<String?>(null) }
    var selectedDevice by remember { mutableStateOf<Device?>(null) }
    var selectedAutomations by remember { mutableStateOf<List<String>>(emptyList()) }

    val filteredDevices = deviceViewModel.devices.filter { it.type == selectedType }

    NewRoutineScreen(
        routineName = routineName,
        onNameChange = { routineName = it },
        selectedIcon = selectedIcon,
        onIconSelected = { selectedIcon = it },
        deviceTypes = listOf("Light", "AC", "Vacuum", "Tap"),
        selectedType = selectedType,
        onTypeSelected = {
            selectedType = it
            selectedDevice = null  // Reset selected device when type changes
            selectedAutomations = emptyList() // Reset automations when type changes
        },
        devices = filteredDevices,
        selectedDevice = selectedDevice,
        onDeviceSelected = { selectedDevice = it },
        automations = when (selectedType) {
            "Light" -> listOf("Set Color", "Set Brightness")
            "AC" -> listOf("Set Temperature", "Set Mode")
            "Vacuum" -> listOf("Start Cleaning", "Stop Cleaning")
            "Tap" -> listOf("Open Tap", "Close Tap")
            else -> emptyList()
        },
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
            val newRoutine = Routine(
                id = UUID.randomUUID().toString(),
                name = routineName,
                icon = selectedIcon ?: 0,
                automations = selectedAutomations
            )
            deviceViewModel.addRoutine(newRoutine)
        },
        onRoutineSaved = {
            routineName = ""
            selectedIcon = null
            selectedType = null
            selectedDevice = null
            selectedAutomations = emptyList()
            onRoutineSaved() // Go back to home screen
        },
        onCancel = {
            routineName = ""
            selectedIcon = null
            selectedType = null
            selectedDevice = null
            selectedAutomations = emptyList()
            onCancel() // Go back to home screen
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewRoutineScreenPreview() {
    NewRoutineScreenState(onRoutineSaved = {}, onCancel = {})
}
