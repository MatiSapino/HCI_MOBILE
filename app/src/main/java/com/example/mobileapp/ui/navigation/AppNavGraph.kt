package com.example.mobileapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileapp.ui.devices.ACCard
import com.example.mobileapp.ui.devices.DoorCard
import com.example.mobileapp.ui.devices.LightCard
import com.example.mobileapp.ui.devices.TapCard
import com.example.mobileapp.ui.devices.VacuumCard
import com.example.mobileapp.ui.views.ConfigurationScreen
import com.example.mobileapp.ui.views.MainScreen
import com.example.mobileapp.ui.views.NewDeviceScreen
import com.example.mobileapp.ui.views.NewRoutineScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(AppDestinations.HOME.route) {
            MainScreen()
        }
        composable(AppDestinations.CONFIGURATION.route) {
            ConfigurationScreen()
        }
        composable(AppDestinations.NEWDEVICE.route) {
            NewDeviceScreen()
        }
        composable(AppDestinations.NEWROUTINE.route) {
            NewRoutineScreen()
        }
        composable(AppDestinations.LIGHT.route) {
            LightCard()
        }
        composable(AppDestinations.TAP.route) {
            TapCard()
        }
        composable(AppDestinations.VACUUM.route) {
            VacuumCard()
        }
        composable(AppDestinations.AC.route) {
            ACCard()
        }
        composable(AppDestinations.DOOR.route) {
            DoorCard()
        }
    }
}