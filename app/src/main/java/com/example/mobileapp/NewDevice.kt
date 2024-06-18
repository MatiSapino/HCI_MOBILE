package com.example.mobileapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.UUID

@Composable
fun NewDeviceScreen(onDeviceAdded: (Device) -> Unit, onCancel: () -> Unit) {
    val deviceViewModel: DeviceViewModel = viewModel()
    var deviceName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<String?>(null) }
    val deviceTypes = listOf("Light", "AC", "Vacuum", "Tap")
    val icons = listOf(
        R.drawable.lightbulb,
        R.drawable.ac,
        R.drawable.vacuum,
        R.drawable.tap,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
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
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color(0xFF87CEEB),
                focusedLabelColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Select Device Type",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            items(deviceTypes.size) { index ->
                val type = deviceTypes[index]
                val icon = icons[index]
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedType == type) Color.Gray else Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .shadow(10.dp, RoundedCornerShape(8.dp))
                        .clickable { selectedType = type }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { selectedType = type },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = Color.Black,
                                selectedColor = Color(0xFF87CEEB)
                            )
                        )
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = type,
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = type, modifier = Modifier.padding(start = 8.dp))
                    }
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
                .shadow(10.dp, RoundedCornerShape(4.dp))
                .size(width = 200.dp, height = 48.dp)
        ) {
            Text("Add Device")
        }
    }
}

