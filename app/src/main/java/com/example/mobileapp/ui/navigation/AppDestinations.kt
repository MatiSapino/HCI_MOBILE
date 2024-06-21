package com.example.mobileapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mobileapp.R

enum class AppDestinations(@StringRes val title: Int, val icon: ImageVector, val route: String) {
    HOME(R.string.Home, Icons.Filled.Home, "home_screen"),
//    CONFIGURATION(R.string.Configuration, Icons.Filled.Settings , "configuration_screen"),

//    NEW_DEVICE(R.string.NewDevice, Icons.Filled.Settings , "new_device_screen"),
//    NEW_ROUTINE(R.string.NewRoutine, Icons.Filled.Settings , "new_routine_screen"),
//    LIGHT(R.string.Light, Icons.Filled.Settings , "light_card/{deviceId}"),
//    TAP(R.string.Tap, Icons.Filled.Settings , "tap_card/{deviceId}"),
//    VACUUM(R.string.Vacuum, Icons.Filled.Settings , "vacuum_card/{deviceId}"),
//    AC(R.string.Ac, Icons.Filled.Settings , "ac_card/{deviceId}"),
//    DOOR(R.string.Door, Icons.Filled.Settings , "door_card/{deviceId}"),
}