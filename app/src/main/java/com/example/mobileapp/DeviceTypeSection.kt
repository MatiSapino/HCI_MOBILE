package com.example.mobileapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeviceTypeSection(
    deviceTypes: List<String>,
    selectedType: String?,
    onDeviceTypeSelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        deviceTypes.forEach { type ->
            Button(
                onClick = { onDeviceTypeSelected(if (selectedType == type) null else type) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == type) Color.Gray else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = type)
            }
        }
    }
}