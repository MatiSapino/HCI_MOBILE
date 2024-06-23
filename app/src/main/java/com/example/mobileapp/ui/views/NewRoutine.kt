package com.example.mobileapp.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.data.model.Routine
import com.example.mobileapp.ui.view_models.DevicesViewModel
import com.example.mobileapp.ui.view_models.RoutinesViewModel
import com.example.mobileapp.ui.view_models.getViewModelFactory

private val selectedColor = Color.Gray
private val unselectedColor = Color.Transparent

@Composable
fun RoutineNameInput(name: String, onNameChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Routine Name") },
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color(0xFF87CEEB),
            focusedLabelColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun IconSelection(selectedIcon: Int?, onIconSelected: (Int) -> Unit) {
    val icons = listOf(
        R.drawable.day,
        R.drawable.home,
        R.drawable.work,
        R.drawable.night,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        icons.forEachIndexed { index, icon ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedIcon == index) Color.Gray else Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .shadow(10.dp, RoundedCornerShape(8.dp))
                    .clickable { onIconSelected(index) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onIconSelected(index) }
                            .background(if (selectedIcon == index) selectedColor else unselectedColor),
                    )
                }
            }
        }
    }
}

@Composable
fun DeviceTypeSelection(deviceTypes: List<DeviceType>, selectedType: DeviceType?, onTypeSelected: (DeviceType) -> Unit) {
    val icons = listOf(
        R.drawable.lightbulb,
        R.drawable.ac,
        R.drawable.vacuum,
        R.drawable.tap,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        deviceTypes.forEachIndexed { index, type ->
            val icon = icons[index]
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedType == type) Color.Gray else Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .shadow(10.dp, RoundedCornerShape(8.dp))
                    .clickable { onTypeSelected(type) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = type.toString(),
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = type.toString(),
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
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
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedDevice == device) Color.Gray else Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .shadow(10.dp, RoundedCornerShape(8.dp))
                    .clickable { onDeviceSelected(device) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = device.name,
                        color = Color.Black
                    )
                }
            }
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
            Button(
                onClick = onAcceptAutomation,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 16.dp
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .shadow(10.dp, RoundedCornerShape(4.dp))
                    .size(width = 100.dp, height = 48.dp)
            ) {
                Text("Accept Automation")
            }
        }
    }
}

@Composable
fun SelectedActions(icon: Int?, actions: List<String>, onSaveRoutine: () -> Unit) {
    val icons = listOf(
        R.drawable.day,
        R.drawable.home,
        R.drawable.work,
        R.drawable.night,
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        icon?.let {
            Image(
                painter = painterResource(id = icons[it]),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
        actions.forEach { action ->
            Text(text = action, modifier = Modifier.padding(vertical = 4.dp))
        }
        Button(
            onClick = onSaveRoutine,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
                .shadow(10.dp, RoundedCornerShape(4.dp))
                .size(width = 200.dp, height = 48.dp)
        ) {
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
    selectedType: DeviceType?,
    onTypeSelected: (DeviceType) -> Unit,
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

    val deviceTypes = DeviceType.entries

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(Color.White, Color(0xFF87CEEB))
            )
        )
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
    val deviceViewModel: DevicesViewModel = viewModel()
    var routineName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf<Int?>(null) }
    var selectedType by remember { mutableStateOf<DeviceType?>(null) }
    var selectedDevice by remember { mutableStateOf<Device?>(null) }
    var selectedAutomations by remember { mutableStateOf<List<String>>(emptyList()) }
    var devices by remember { mutableStateOf(deviceViewModel.uiState.value.devices) }

    val filteredDevices = if (selectedType != null) devices.filter { it.type == selectedType } else devices

    val routineViewModel: RoutinesViewModel = viewModel(factory = getViewModelFactory())

    NewRoutineScreen(
        routineName = routineName,
        onNameChange = { routineName = it },
        selectedIcon = selectedIcon,
        onIconSelected = { selectedIcon = it },
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
            DeviceType.LAMP -> listOf("Turn On","Turn Off","Set Color", "Set Brightness")
            DeviceType.AC -> listOf("Turn On","Turn Off","Set Temperature", "Set Mode", "Set Speed", "Set Vertical Swing", "Set Horizontal Swing")
            DeviceType.VACUUM -> listOf("Start Cleaning", "Stop Cleaning", "Dock", "Set Mode")
            DeviceType.TAP -> listOf("Open Tap", "Close Tap", "Dispense")
            DeviceType.DOOR -> listOf("Open Door", "Close Door", "Lock Door", "Unlock Door'")
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
//            val newRoutine = Routine(
//                id = null,
//                name = routineName,
//                actions = null,
//                meta = null
//            )
            routineViewModel.addRoutine(routineId = "a")
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