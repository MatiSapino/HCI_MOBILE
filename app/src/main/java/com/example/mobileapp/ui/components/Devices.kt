package com.example.mobileapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R
import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType

@Composable
fun DeviceSection(
    devices: List<Device>,
    onDeviceSelected: (Device) -> Unit,
    onAddDevice: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Devices", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(7.dp))
                IconButton(onClick = onAddDevice) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Device")
                }
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(devices) { device ->
                DeviceCard(device = device, onClick = { onDeviceSelected(device) })
            }
        }
    }
}

@Composable
fun DeviceCard(device: Device, onClick: () -> Unit) {
    val icons = mapOf(
        DeviceType.LAMP to R.drawable.lightbulb,
        DeviceType.AC to R.drawable.ac,
        DeviceType.VACUUM to R.drawable.vacuum,
        DeviceType.TAP to R.drawable.tap,
        DeviceType.DOOR to R.drawable.door, // change to correct icon
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .width(100.dp)
            .height(70.dp)
            .padding(horizontal = 8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .shadow(10.dp, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val iconId = icons[device.type] ?: R.drawable.lightbulb
            Image(
                painter = painterResource(id = iconId),
                contentDescription = device.type.toString(),
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = device.name,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}