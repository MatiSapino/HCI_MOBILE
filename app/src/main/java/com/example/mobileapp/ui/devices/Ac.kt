package com.example.mobileapp.ui.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.ui.view_models.devices.AcViewModel

@Composable
fun ACCard(
    vm: AcViewModel,
    onBack: () -> Unit,
) {
    var ac by remember { mutableStateOf(vm.uiState.value.currentDevice) }
    var acState by remember { mutableStateOf(vm.uiState.value.currentDevice?.status) }
    var verticalSwing by remember { mutableStateOf(vm.uiState.value.currentDevice?.verticalSwing) }
    var horizontalSwing by remember { mutableStateOf(vm.uiState.value.currentDevice?.horizontalSwing) }
    var fanSpeed by remember { mutableStateOf(vm.uiState.value.currentDevice?.fanSpeed) }
    var temperature by remember { mutableFloatStateOf(vm.uiState.value.currentDevice?.temperature!!.toFloat()) }
    var mode by remember { mutableStateOf(vm.uiState.value.currentDevice?.mode) }

    val velocityOptions = listOf("auto", "25", "50", "75", "100")
    val verticalSwingsOptions = listOf("auto", "22", "45", "67", "90")
    val horizontalSwingsOptions = listOf("auto", "-90", "-45", "0", "45", "90")
    val modesAc = listOf("fan", "cool", "heat")

    val acSpeed = remember { mutableIntStateOf(velocityOptions.indexOf(fanSpeed)) }
    val acVertical = remember { mutableIntStateOf(verticalSwingsOptions.indexOf(verticalSwing)) }
    val acHorizontal = remember { mutableIntStateOf(horizontalSwingsOptions.indexOf(horizontalSwing)) }


    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
            .padding(10.dp)
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "AC - ${ac?.name}", fontSize = 20.sp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = acState == Status.ON,
                        onCheckedChange = { newStatus ->
                            acState = if (newStatus) Status.ON else Status.OFF
                            if (acState == Status.ON) vm.turnOn()
                            if (acState == Status.OFF) vm.turnOff()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF87CEEB),
                            checkedTrackColor = Color.Gray,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White
                        )
                    )
                    Text(text = acState.toString(), color = Color.Black, fontSize = 16.sp)
                }

                // Temperature Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Temperature: ${temperature.toInt()}°C", color = Color.Black)
                    Slider(
                        value = temperature,
                        onValueChange = { newTemperature ->
                            temperature = newTemperature
                            vm.setTemperature(temperature.toInt())
                        },
                        valueRange = 18f..38f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF87CEEB),
                            activeTrackColor = Color(0xFF87CEEB)
                        )
                    )
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
                            modesAc.forEach { modeAc ->
                                DropdownMenuItem(
                                    text = { Text(text = modeAc, color = Color.Black) },
                                    onClick = {
                                        mode = modeAc
                                        vm.setMode(modeAc)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Fan Speed Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Fan Speed: ${velocityOptions[acSpeed.intValue]}", color = Color.Black)
                    Slider(
                        value = acSpeed.intValue.toFloat(),
                        onValueChange = {
                            acSpeed.intValue = it.toInt()
                            vm.setFanSpeed(velocityOptions[acSpeed.intValue])
                                        },
                        valueRange = 0f..4f,
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF87CEEB),
                            activeTrackColor = Color(0xFF87CEEB)
                        )
                    )
                }

                // Vertical Swing Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Vertical Swing: ${verticalSwingsOptions[acVertical.intValue]}", color = Color.Black)
                    Slider(
                        value = acVertical.intValue.toFloat(),
                        onValueChange = {
                            acVertical.intValue = it.toInt()
                            vm.setVerticalSwing(verticalSwingsOptions[acVertical.intValue])

                                        },
                        valueRange = 0f..4f,
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF87CEEB),
                            activeTrackColor = Color(0xFF87CEEB)
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Horizontal Swing: ${horizontalSwingsOptions[acHorizontal.intValue]}", color = Color.Black)
                    Slider(
                        value = acHorizontal.intValue.toFloat(),
                        onValueChange = {
                            acHorizontal.intValue = it.toInt()
                            vm.setHorizontalSwing(horizontalSwingsOptions[acHorizontal.intValue])
                                        },
                        valueRange = 0f..5f,
                        steps = 5,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF87CEEB),
                            activeTrackColor = Color(0xFF87CEEB)
                        )
                    )
                }

                Button(
                    onClick = {
                        vm.deleteDevice(ac?.id)
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
fun PreviewACCard() {
//    ACCard(
//        device = Device(
//            id = "1",
//            name = "AC",
//            type = "AC",
//            state = mutableMapOf("temperature" to 25f, "mode" to "Cool")
//        ),
//        onBack = {},
//        onDelete = {},
//        onUpdateDevice = {}
//    )
}