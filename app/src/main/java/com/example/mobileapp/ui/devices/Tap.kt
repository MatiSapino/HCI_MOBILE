package com.example.mobileapp.ui.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.mobileapp.ui.view_models.devices.TapViewModel
import com.example.mobileapp.data.model.Unit

@Composable
fun TapCard(
    vm: TapViewModel,
    onBack: () -> kotlin.Unit
) {
    val uiTapState by vm.uiState.collectAsState()

    var tapState by remember { mutableStateOf<Status?>(null) }
    var quantity by remember { mutableFloatStateOf(0.toFloat()) }
    var unit by remember { mutableStateOf(Unit.L) }

    // Update states when uiDoorState.currentDevice becomes available
    LaunchedEffect(uiTapState.currentDevice) {
        uiTapState.currentDevice?.let { device ->
            tapState = device.status
        }
    }

    val units = Unit.entries

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
            .fillMaxSize()
    ) {
        IconButton(onClick =  {onBack()} , modifier = Modifier.align(Alignment.Start)) {
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
                Text(text = "Tap - ${uiTapState.currentDevice?.name}", fontSize = 20.sp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = tapState == Status.OPENED,
                        onCheckedChange = { newStatus ->
                            tapState = if (newStatus) Status.OPENED else Status.CLOSED
                            if (tapState == Status.OPENED) vm.open()
                            if (tapState == Status.CLOSED) vm.close()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF87CEEB),
                            checkedTrackColor = Color.Gray,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White
                        )
                    )
                    Text(text = tapState.toString(), color = Color.Black, fontSize = 16.sp)
                }

                // Quantity Slider
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Quantity: ${quantity.toInt()} $unit", color = Color.Black)
                    Slider(
                        value = quantity,
                        onValueChange = { newQuantity ->
                            quantity = newQuantity
                        },
                        valueRange = 0f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF87CEEB),
                            activeTrackColor = Color(0xFF87CEEB)
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Unit: $unit",
                            modifier = Modifier
                                .clickable { expanded = true }
                                .padding(16.dp),
                            color = Color.Black
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            units.forEach { u ->
                                DropdownMenuItem(
                                    text = { Text(text = u.toString(), color = Color.Black) },
                                    onClick = {
                                        unit = u
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        vm.dispense(quantity.toInt(), unit)
                              // alert("Dispensed {quantity.toInt()} {unit.toString()} ")
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFF87CEEB),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Dispense")
                }

                Button(
                    onClick = {
                        vm.deleteDevice(uiTapState.currentDevice?.id)
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
fun TapCardPreview() {
//    TapCard(
//        device = Device(
//            id = "1",
//            name = "Tap",
//            type = "Tap",
//            state = mutableMapOf("0" to 0f, "L" to "L")
//        ),
//        onBack = {},
//        onDelete = {},
//        onUpdateDevice = {}
//    )
}