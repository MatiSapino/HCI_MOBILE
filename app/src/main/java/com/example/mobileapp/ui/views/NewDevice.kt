package com.example.mobileapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.ui.components.Device
import com.example.mobileapp.ui.components.DeviceViewModel
import java.util.UUID

@Composable
fun NewDeviceScreen(onDeviceAdded: (Device) -> Unit, onCancel: () -> Unit) {
    val deviceViewModel: DeviceViewModel = viewModel()
    var deviceName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<String?>(null) }
    val deviceTypes = listOf("Light", "AC", "Vacuum", "Tap")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(onClick = onCancel, modifier = Modifier.align(Alignment.Start)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        TextField(
            value = deviceName,
            onValueChange = { deviceName = it },
            label = { Text("Device Name") },
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn {
            items(deviceTypes) { type ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedType = type },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedType == type,
                        onClick = { selectedType = type }
                    )
                    Text(text = type, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
        Button(
            onClick = {
                if (deviceName.isNotEmpty() && selectedType != null) {
                    val newDevice = Device(
                        id = UUID.randomUUID().toString(),
                        name = deviceName,
                        type = selectedType!!,
                        state = mutableMapOf("status" to false, "color" to "White", "brightness" to 100) // Example initial state
                    )
                    deviceViewModel.addDevice(newDevice)
                    deviceName = ""
                    selectedType = null
                    onDeviceAdded(newDevice)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Device")
        }
    }
}
