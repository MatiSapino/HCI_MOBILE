package com.example.mobileapp.ui.devices

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.components.Device

@Composable
fun VacuumCard(
    device: Device,
    onBack: () -> Unit,
    onDelete: (Device) -> Unit,
    onUpdateDevice: (Device) -> Unit
) {
    var status by remember { mutableStateOf(device.state["inactive"] as String) }
    var mode by remember { mutableStateOf(device.state["vacuum"] as String) }

    val selectedRoom = remember { mutableStateOf<String?>(null) }
    val showDetails = remember { mutableStateOf(false) }

    val modesVacuum = listOf("vacuum", "mop")
    val roomsVacuum = listOf("Living Room", "Kitchen", "Bedroom", "Bathroom", "Garage", "Garden")

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Vacuum - ${device.name}")
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = status == "active",
                    onCheckedChange = { newStatus ->
                        status = if (newStatus) "active" else "inactive"
                        val updatedDevice = device.copy(state = device.state.apply { put("status", status) })
                        onUpdateDevice(updatedDevice)
                    }
                )
                Text(text = status)
            }

            // Mode Selector
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Mode: $mode",
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(16.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        modesVacuum.forEach { modeVacuum ->
                            DropdownMenuItem(
                                text = { Text(text = mode) },
                                onClick = {
                                    mode = modeVacuum
                                    device.state["mode"] = mode
                                    onUpdateDevice(device)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Room Selector
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Room: ${selectedRoom.value ?: "Select Room"}",
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(16.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        roomsVacuum.forEach { room ->
                            DropdownMenuItem(
                                text = { Text(text = room) },
                                onClick = {
                                    selectedRoom.value = room
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (showDetails.value) {
                // Back to Base Button
                Button(
                    onClick = {
                        // Implement logic to return to base
                        if (status == "inactive") {
                            // Show alert logic
                        } else {
                            status = "inactive"
                            selectedRoom.value = null
                            // Show alert logic
                        }
                    },
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Back to Base")
                }

                HorizontalDivider()

                // Delete Device Button
                Button(
                    onClick = { onDelete(device) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Delete Device")
                }
            }

            HorizontalDivider()

            Button(
                onClick = { showDetails.value = !showDetails.value },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = if (!showDetails.value) "More" else "Close")
            }
        }
    }
}