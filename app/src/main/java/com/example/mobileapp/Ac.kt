package com.example.mobileapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ACCard() {
    val acState = remember { mutableStateOf("off") }
    val acTemperature = remember { mutableFloatStateOf(25f) }
    val acMode = remember { mutableStateOf("Cool") }
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
            Text(text = "AC - Name")
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
                Text(text = "Temperature: ${acTemperature.floatValue.toInt()}°C")
                Slider(
                    value = acTemperature.floatValue,
                    onValueChange = { acTemperature.floatValue = it },
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
                        text = "Mode: ${acMode.value}",
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(16.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        modesAc.forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(text = mode) },
                                onClick = {
                                    acMode.value = mode
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
                    onClick = { /* Delete Device Logic */ },
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
fun ACCardPreview() {
    ACCard()
}
