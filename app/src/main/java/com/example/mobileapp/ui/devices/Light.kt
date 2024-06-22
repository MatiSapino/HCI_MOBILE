package com.example.mobileapp.ui.devices

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.ui.view_models.devices.LampViewModel


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
            modifier = Modifier
                .padding(16.dp)
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
    vm: LampViewModel,
    onBack: () -> Unit,
) {
    val uiLampState by vm.uiState.collectAsState()

    var lightState by remember { mutableStateOf<Status?>(null) }
    var color by remember { mutableStateOf<String?>("#FFFFFF") }
    var brightness by remember { mutableStateOf<Int?>(0) }

    // Update states when uiLampState.currentDevice becomes available
    LaunchedEffect(uiLampState.currentDevice) {
        uiLampState.currentDevice?.let { device ->
            brightness = device.brightness
            color = device.color
            lightState = device.status
        }
    }

    val showColorPicker = remember { mutableStateOf(false) }

    fun colorToHex(color: Color): String {
        val argb = color.toArgb()
        return String.format("#%08X", argb)
    }

    fun hexToColor(hex: String?): Color {
        if (hex.isNullOrEmpty()) {
            Log.e("ColorConversion", "Provided hex string is null or empty")
            return Color(0xFFFFFFFF) // Default to white if empty or null
        }

        val hexCleaned = if (hex.startsWith("#")) hex.substring(1) else hex

        // Ensure the cleaned hex string has valid length (6 for RGB or 8 for ARGB)
        if (hexCleaned.length != 6 && hexCleaned.length != 8) {
            Log.e("ColorConversion", "Invalid hex string length: $hexCleaned")
            return Color(0xFFFFFFFF) // Default to white for invalid length
        }

        return try {
            // If length is 6 (RGB), add full opacity (FF) at the beginning
            val argbHex = if (hexCleaned.length == 6) "FF$hexCleaned" else hexCleaned
            val value = argbHex.toULong(16)
            Color(value)
        } catch (e: NumberFormatException) {
            Log.e("ColorConversion", "NumberFormatException for hex: $hexCleaned", e)
            Color(0xFFFFFFFF) // Default to white if conversion fails
        } catch (e: Exception) {
            Log.e("ColorConversion", "Exception for hex: $hexCleaned", e)
            Color(0xFFFFFFFF) // Default to white if conversion fails
        }
    }


    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
            .fillMaxSize()
    ) {
        IconButton(onClick = { onBack() }, modifier = Modifier.align(Alignment.Start)) {
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
                Text(text = "Light - ${uiLampState.currentDevice?.name}", fontSize = 20.sp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = lightState == Status.ON,
                        onCheckedChange = { newStatus ->
                            lightState = if (newStatus) Status.ON else Status.OFF
                            if (lightState == Status.ON) vm.turnOn()
                            if (lightState == Status.OFF) vm.turnOff()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF87CEEB),
                            checkedTrackColor = Color.Gray,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White
                        )
                    )
                    Text(text = lightState.toString(), color = Color.Black, fontSize = 16.sp)
                }
                uiLampState.currentDevice?.let {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(hexToColor(it.color))
                            .border(1.dp, Color.Black)
                            .clickable {
                                showColorPicker.value = true
                            }
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Brightness: ${brightness}%", color = Color.Black, modifier = Modifier.padding(top = 8.dp))
                    Slider(
                        value = brightness?.toFloat() ?: 0f,
                        onValueChange = { newBrightness ->
                            brightness = newBrightness.toInt()
                            vm.setBrightness(brightness!!)
                        },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF87CEEB),
                            activeTrackColor = Color(0xFF87CEEB)
                        )
                    )
                }

                Button(
                    onClick = {
                        vm.deleteDevice(uiLampState.currentDevice?.id)
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
                    Text(text = "Delete Light")
                }
            }
        }
    }

    if (showColorPicker.value) {
        uiLampState.currentDevice?.let {
            ColorPickerDialog(
                selectedColor = hexToColor(it.color),
                onColorSelected = { selectedColor ->
                    color = colorToHex(selectedColor)
                    vm.setColor(color!!)
                },
                onDismissRequest = { showColorPicker.value = false }
            )
        }
    }
}

