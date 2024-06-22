package com.example.mobileapp.ui.devices

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

    var light by remember { mutableStateOf(uiLampState.currentDevice) }
    var lightState by remember { mutableStateOf(uiLampState.currentDevice?.status) }
    var color by remember { mutableStateOf(uiLampState.currentDevice?.color) }
    var brightness by remember { mutableStateOf(uiLampState.currentDevice?.brightness) }

    val showColorPicker = remember { mutableStateOf(false) }

    fun colorToHex(color: Color): String {
        val argb = color.toArgb()
        return String.format("#%08X", argb)
    }

    fun startsWith(str: String, prefix: String): Boolean {
        if (prefix.length > str.length) return false
        for (i in prefix.indices) {
            if (str[i] != prefix[i]) return false
        }
        return true
    }

    fun substring(str: String, beginIndex: Int, endIndex: Int): String {
        if (beginIndex !in 0..endIndex || endIndex !in 0..str.length) throw IndexOutOfBoundsException("beginIndex: $beginIndex, endIndex: $endIndex, length: $str.length")
        val length = endIndex - beginIndex
        if (length == 0) return ""
        val result = CharArray(length)
        for (i in 0 until length) {
            result[i] = str[beginIndex + i]
        }
        return String(result)
    }

    fun hexToColor(hex: String): Color{
        val hexa = if (startsWith(hex, "#")) substring(hex, 0,1) else hex
            val value = hexa.toULong(16)
            return Color(value)
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
        IconButton(onClick = {onBack()}, modifier = Modifier.align(Alignment.Start)) {
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
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Light - ${light?.name}", fontSize = 20.sp, color = Color.Black)

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

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(hexToColor(color!!))
                        .border(1.dp, Color.Black)
                        .clickable {
                            showColorPicker.value = true
                        }
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Brightness: ${brightness}%", color = Color.Black, modifier = Modifier.padding(top = 8.dp))
                    Slider(
                        value = brightness?.toFloat()!!,
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
                        vm.deleteDevice(light?.id)
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
        ColorPickerDialog(
            selectedColor = hexToColor(color!!),
            onColorSelected = { selectedColor ->
                color = colorToHex(selectedColor)
                vm.setColor(color!!)
            },
            onDismissRequest = { showColorPicker.value = false }
        )
    }
}
