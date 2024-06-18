package com.example.mobileapp.ui.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ColorPickerDialog(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta, Color.Black, Color.White)
    var currentSelectedColor by remember { mutableStateOf(selectedColor) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Select a color")
                Spacer(modifier = Modifier.padding(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(colors) { color ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(color)
                                .clickable {
                                    currentSelectedColor = color
                                    onColorSelected(color)
                                    onDismissRequest()
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (color == currentSelectedColor) Color.Black else Color.Transparent
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LightCard(
    device: Device,
    onBack: () -> Unit,
    onDelete: (Device) -> Unit,
    onUpdateDevice: (Device) -> Unit
) {
    val lightState = remember { mutableStateOf("off") }

    var color by remember { mutableStateOf(device.state["color"] as Color) }
    var brightness by remember { mutableIntStateOf(device.state["brightness"] as Int) }

    val showColorPicker = remember { mutableStateOf(false) }
    val showDetails = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Light - ${device.name}")
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = lightState.value == "on",
                    onCheckedChange = {
                        lightState.value = if (it) "on" else "off"
                    }
                )
                Text(text = lightState.value)
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Black)
                    .clickable {
                        showColorPicker.value = true
                    }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Brightness: ${brightness}%")
                Slider(
                    value = brightness.toFloat(),
                    onValueChange = { newBrightness ->
                        brightness = newBrightness.toInt()
                        val updatedDevice = device.copy(state = device.state.apply { put("brightness", brightness) })
                        onUpdateDevice(updatedDevice)
                    },
                    valueRange = 0f..100f
                )
            }

            if (showDetails.value) {
                HorizontalDivider()
                Button(
                    onClick = {
                        onDelete(device)
                        onBack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Delete Light")
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

    if (showColorPicker.value) {
        ColorPickerDialog(
            selectedColor = color,
            onColorSelected = { selectedColor ->
                color = selectedColor
                val updatedDevice = device.copy(state = device.state.apply { put("color", selectedColor) })
                onUpdateDevice(updatedDevice)
            },
            onDismissRequest = { showColorPicker.value = false }
        )
    }
}