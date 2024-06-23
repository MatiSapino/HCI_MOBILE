package com.example.mobileapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R
import com.example.mobileapp.data.model.DeviceType
import kotlin.enums.EnumEntries

@Composable
fun DeviceTypeSection(
    deviceTypes: EnumEntries<DeviceType>,
    selectedType: DeviceType?,
    onDeviceTypeSelected: (DeviceType?) -> Unit
) {
    val icons = listOf(
        R.drawable.lightbulb,
        R.drawable.ac,
        R.drawable.tap,
        R.drawable.door,
        R.drawable.vacuum,

    )
    Text(
        text = stringResource(id = R.string.types),
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 2.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        deviceTypes.forEachIndexed { index, type ->
            val icon = icons[index]
            Card(
                shape = RoundedCornerShape(13.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedType == type) Color.Gray else Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(13.dp))
                    .shadow(10.dp, RoundedCornerShape(13.dp))
                    .clickable { onDeviceTypeSelected(if (selectedType == type) null else type) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = type.toString(),
                        modifier = Modifier.size(17.dp),
                    )
//                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = type.name,
                        fontSize = 9.sp,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
