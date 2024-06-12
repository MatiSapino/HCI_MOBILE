package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
fun LightCard() {
    val lightState = remember { mutableStateOf("off") }
    val lightColor = remember { mutableStateOf(Color.Black) }
    val brightness = remember { mutableFloatStateOf(50f) }
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
            Text(text = "Light - Name")
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
                    .background(lightColor.value)
                    .clickable {
                        showColorPicker.value = true
                    }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Brightness: ${brightness.floatValue.toInt()}%")
                Slider(
                    value = brightness.floatValue,
                    onValueChange = { brightness.floatValue = it },
                    valueRange = 0f..100f
                )
            }

            if (showDetails.value) {
                HorizontalDivider()
                Button(
                    onClick = { /* Delete Light Logic */ },
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
            selectedColor = lightColor.value,
            onColorSelected = { lightColor.value = it },
            onDismissRequest = { showColorPicker.value = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LightCardPreview() {
    LightCard()
}