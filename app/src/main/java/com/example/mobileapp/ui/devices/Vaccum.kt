package com.example.mobileapp.ui.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.ui.view_models.devices.VacuumViewModel

@Composable
fun VacuumCard(
    vm: VacuumViewModel,
    onBack: () -> Unit
) {
    val uiVacuumState by vm.uiState.collectAsState()

    var vacuumState by remember { mutableStateOf<Status?>(null) }
    var mode by remember { mutableStateOf<String?>("") }
    var selectedRoom by remember { mutableStateOf<String?>("") }

    // Update states when uiDoorState.currentDevice becomes available
    LaunchedEffect(uiVacuumState.currentDevice) {
        uiVacuumState.currentDevice?.let { device ->
            vacuumState = device.status
        }
    }

    val modesVacuum = listOf("vacuum", "mop")
    val roomsVacuum = listOf("Living Room", "Kitchen", "Bedroom", "Bathroom", "Garage", "Garden")

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf( Color(0xFFF0EDCF), Color(0xFF40A2D8))
                )
            )
            .fillMaxSize()
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(10.dp, RoundedCornerShape(16.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Vacuum - ${uiVacuumState.currentDevice?.name}", fontSize = 20.sp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = vacuumState == Status.ACTIVE,
                        onCheckedChange = { newvacuumState ->
                            vacuumState = if (newvacuumState) Status.ACTIVE else Status.INACTIVE
                            if (vacuumState == Status.ACTIVE) vm.start()
                            if (vacuumState == Status.INACTIVE) vm.pause()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF87CEEB),
                            checkedTrackColor = Color.Gray,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White
                        )
                    )
                    Text(text = vacuumState.toString(), color = Color.Black, fontSize = 16.sp)
                }

                // Mode Selector
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Mode: $mode",
                            modifier = Modifier
                                .clickable { expanded = true }
                                .padding(16.dp),
                            color = Color.Black
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            modesVacuum.forEach { modeVacuum ->
                                DropdownMenuItem(
                                    text = { Text(text = modeVacuum, color = Color.Black) },
                                    onClick = {
                                        mode = modeVacuum
                                        vm.setMode(modeVacuum)
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
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Room: ${selectedRoom ?: "Select Room"}",
                            modifier = Modifier
                                .clickable { expanded = true }
                                .padding(16.dp),
                            color = Color.Black
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            roomsVacuum.forEach { room ->
                                DropdownMenuItem(
                                    text = { Text(text = room, color = Color.Black) },
                                    onClick = {
                                        selectedRoom = room
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        // Implement logic to return to base
                        if (vacuumState == Status.INACTIVE) {
                            // alert("Error: Vacuum already inactive")
                        } else {
                            vacuumState = Status.INACTIVE
                            selectedRoom = null
                            vm.dock()
                            // alert("Vacuum docked")
                        }
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFF87CEEB),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Back to Base")
                }

                Button(
                    onClick = {
                        vm.deleteDevice(uiVacuumState.currentDevice?.id)
                        onBack()
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Delete Device")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VacuumCardPreview() {
//    VacuumCard(
//        device = Device(
//            id = "1",
//            name = "Living Room Vacuum",
//            type = "Vacuum",
//            state = mutableMapOf("vacuumState" to "inactive", "vacuum" to "vacuum", "room" to "Living Room")
//        ),
//        onBack = {},
//        onDelete = {},
//        onUpdateDevice = {}
//    )
}