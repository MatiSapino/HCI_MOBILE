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
import androidx.compose.runtime.collectAsState
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
import androidx.core.widget.ContentLoadingProgressBar
import com.example.mobileapp.data.model.Status
import com.example.mobileapp.ui.view_models.devices.AcViewModel
import kotlinx.coroutines.delay

@Composable
fun ACCard(
    vm: AcViewModel,
    onBack: () -> Unit,
) {
    val uiLampState by vm.uiState.collectAsState()

    val velocityOptions = listOf("auto", "25", "50", "75", "100")
    val verticalSwingsOptions = listOf("auto", "22", "45", "67", "90")
    val horizontalSwingsOptions = listOf("auto", "-90", "-45", "0", "45", "90")
    val modesAc = listOf("fan", "cool", "heat")

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
            .padding(10.dp)
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
                Text(
                    text = "AC - ${uiLampState.currentDevice?.name ?: "Loading"}",
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var acState by remember {
                        mutableStateOf(uiLampState.currentDevice?.status)
                    }
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
                    var temperature by remember {
                        mutableStateOf(uiLampState.currentDevice?.let {
                            it.temperature?.toFloat()
                        } ?: 0.toFloat())
                    }
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
                            text = "Mode: ${uiLampState.currentDevice?.mode}",
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
                                        uiLampState.currentDevice?.mode = modeAc
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
                    var acSpeed by remember {
                        mutableStateOf(uiLampState.currentDevice?.let {
                            velocityOptions.indexOf(it.fanSpeed)
                        } ?: 0)
                    }
                    Text(text = "Fan Speed: ${velocityOptions[acSpeed]}", color = Color.Black)
                    Slider(
                        value = acSpeed.toFloat(),
                        onValueChange = {
                            acSpeed = it.toInt()
                            vm.setFanSpeed(velocityOptions[acSpeed])
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
                    var acVertical by remember {
                        mutableStateOf(uiLampState.currentDevice?.let {
                            verticalSwingsOptions.indexOf(it.verticalSwing)
                        } ?: 0)
                    }
                    Text(
                        text = "Vertical Swing: ${verticalSwingsOptions[acVertical]}",
                        color = Color.Black
                    )
                    Slider(
                        value = acVertical.toFloat(),
                        onValueChange = {
                            acVertical = it.toInt()
                            vm.setVerticalSwing(verticalSwingsOptions[acVertical])

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
                    var acHorizontal by remember {
                        mutableStateOf(uiLampState.currentDevice?.let {
                            horizontalSwingsOptions.indexOf(it.horizontalSwing)
                        } ?: 0)
                    }
                    Text(
                        text = "Horizontal Swing: ${horizontalSwingsOptions[acHorizontal]}",
                        color = Color.Black
                    )
                    Slider(
                        value = acHorizontal.toFloat(),
                        onValueChange = {
                            acHorizontal = it.toInt()
                            vm.setHorizontalSwing(horizontalSwingsOptions[acHorizontal])
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
                    Text(text = "Delete Device")
                }
            }
        }
    }
}
