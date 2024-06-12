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
fun TapCard() {
    val tapState = remember { mutableStateOf("closed") }
    val quantity = remember { mutableFloatStateOf(0f) }
    val unit = remember { mutableStateOf("L") }
    val showDetails = remember { mutableStateOf(false) }

    val units = listOf("Ml", "Cl", "Dl", "L")

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
            Text(text = "Tap - Name")
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = tapState.value == "opened",
                    onCheckedChange = {
                        tapState.value = if (it) "opened" else "closed"
                    }
                )
                Text(text = tapState.value)
            }

            // Quantity Slider
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Quantity: ${quantity.floatValue.toInt()} ${unit.value}")
                Slider(
                    value = quantity.floatValue,
                    onValueChange = { quantity.floatValue = it },
                    valueRange = 0f..100f
                )
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Unit: ${unit.value}",
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(16.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        units.forEach { u ->
                            DropdownMenuItem(
                                text = { Text(text = u) },
                                onClick = {
                                    unit.value = u
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    // Implement dispense logic
                    if (tapState.value == "closed") {
                        // Show alert logic
                    } else {
                        // Show dispense alert logic
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Dispense")
            }

            if (showDetails.value) {
                HorizontalDivider()
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
fun TapCardPreview() {
    TapCard()
}
