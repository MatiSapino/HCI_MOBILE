package com.example.mobileapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileapp.DrawableStringPair
import com.example.mobileapp.R

private val devices = listOf(
    DrawableStringPair(R.drawable.ic_launcher_background, R.string.light),
    DrawableStringPair(R.drawable.ic_launcher_foreground, R.string.ac)
)

@Composable
fun DeviceRow(modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
    ) {
        items(items = devices) { item ->
            Device(item.drawable, item.text)
        }
    }
}