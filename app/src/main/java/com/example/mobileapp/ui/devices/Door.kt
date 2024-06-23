package com.example.mobileapp.ui.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.mobileapp.ui.view_models.devices.DoorViewModel

@Composable
fun DoorCard(
    vm: DoorViewModel,
    onBack: () -> Unit,
) {
    val uiDoorState by vm.uiState.collectAsState()
    var doorState by remember { mutableStateOf<Status?>(null) }
    var lock by remember { mutableStateOf<Status?>(null) }

    // Update states when uiDoorState.currentDevice becomes available
    LaunchedEffect(uiDoorState.currentDevice) {
        uiDoorState.currentDevice?.let { device ->
            lock = device.lock
            doorState = device.status
        }
    }

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf( Color(0xFFF0EDCF), Color(0xFF40A2D8))
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
                Text(text = "Door - ${uiDoorState.currentDevice?.name}", fontSize = 20.sp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = doorState == Status.OPENED,
                        onCheckedChange = { newStatus ->
                            doorState = if (newStatus) Status.OPENED else Status.CLOSED
                            if (doorState == Status.OPENED) vm.open()
                            if (doorState == Status.CLOSED) vm.close()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF87CEEB),
                            checkedTrackColor = Color.Gray,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White
                        )
                    )
                    Text(text = doorState.toString(), color = Color.Black, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = lock == Status.LOCKED,
                        onCheckedChange = { newStatus ->
                            doorState = if (newStatus) Status.LOCKED else Status.UNLOCKED
                            if (doorState == Status.LOCKED) vm.lock()
                            if (doorState == Status.UNLOCKED) vm.unlock()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF87CEEB),
                            checkedTrackColor = Color.Gray,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.White
                        )
                    )
                    Text(text = doorState.toString(), color = Color.Black, fontSize = 16.sp)
                }


                Button(
                    onClick = {
                        vm.deleteDevice(uiDoorState.currentDevice?.id)
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
fun PreviewDoorCard() {
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