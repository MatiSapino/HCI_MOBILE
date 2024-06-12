package com.example.mobileapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun VacuumCard(onBack: () -> Unit) {
    val vacuumState = remember { mutableStateOf("inactive") }
    val vacuumMode = remember { mutableStateOf("vacuum") }
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
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Vacuum - Name")
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = vacuumState.value == "active",
                    onCheckedChange = {
                        vacuumState.value = if (it) "active" else "inactive"
                    }
                )
                Text(text = vacuumState.value)
            }

            // Mode Selector
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Mode: ${vacuumMode.value}",
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(16.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        modesVacuum.forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(text = mode) },
                                onClick = {
                                    vacuumMode.value = mode
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
                        if (vacuumState.value == "inactive") {
                            // Show alert logic
                        } else {
                            vacuumState.value = "inactive"
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
                    onClick = { /* Implement delete logic */ },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VacuumCardPreview() {
    VacuumCard(onBack = {})
}
