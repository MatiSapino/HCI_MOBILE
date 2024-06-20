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
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.view_models.devices.DoorViewModel

@Composable
fun DoorCard(
    vm: DoorViewModel
    onBack: () -> Unit,
) {
    val acState = remember { mutableStateOf("off") }

    var temperature by remember { mutableFloatStateOf(device.state["temperature"] as Float) }
    var mode by remember { mutableStateOf(device.state["Cool"] as String) }

    val acSpeed = remember { mutableIntStateOf(2) }
    val acVertical = remember { mutableIntStateOf(2) }
    val acHorizontal = remember { mutableIntStateOf(2) }
    val showDetails = remember { mutableStateOf(false) }

    val velocityOptions = listOf("auto", "25", "50", "75", "100")
    val verticalSwingsOptions = listOf("auto", "22", "45", "67", "90")
    val horizontalSwingsOptions = listOf("auto", "-90", "-45", "0", "45", "90")
    val modesAc = listOf("Fan", "Cool", "Heat")

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
            Text(text = "AC - ${device.name}")
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = acState.value == "on",
                    onCheckedChange = {
                        acState.value = if (it) "on" else "off"
                    }
                )
                Text(text = acState.value)
            }

            // Temperature Slider
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Temperature: ${temperature.toInt()}°C")
                Slider(
                    value = temperature,
                    onValueChange = { newTemperature ->
                        temperature = newTemperature
                        val updatedDevice = device.copy(state = device.state.apply { put("temperature", temperature) })
                        onUpdateDevice(updatedDevice)
                    },
                    valueRange = 18f..38f
                )
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
                        modesAc.forEach { modeAc ->
                            DropdownMenuItem(
                                text = { Text(text = mode) },
                                onClick = {
                                    mode = modeAc
                                    device.state["mode"] = mode
                                    onUpdateDevice(device)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (showDetails.value) {
                // Fan Speed Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Fan Speed: ${velocityOptions[acSpeed.intValue]}")
                    Slider(
                        value = acSpeed.intValue.toFloat(),
                        onValueChange = { acSpeed.intValue = it.toInt() },
                        valueRange = 0f..4f,
                        steps = 4
                    )
                }

                // Vertical Swing Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Vertical Swing: ${verticalSwingsOptions[acVertical.intValue]}")
                    Slider(
                        value = acVertical.intValue.toFloat(),
                        onValueChange = { acVertical.intValue = it.toInt() },
                        valueRange = 0f..4f,
                        steps = 4
                    )
                }

                // Horizontal Swing Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Horizontal Swing: ${horizontalSwingsOptions[acHorizontal.intValue]}")
                    Slider(
                        value = acHorizontal.intValue.toFloat(),
                        onValueChange = { acHorizontal.intValue = it.toInt() },
                        valueRange = 0f..5f,
                        steps = 5
                    )
                }

                HorizontalDivider()
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
