package com.example.mobileapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobileapp.data.model.Device
import com.example.mobileapp.data.model.DeviceType
import com.example.mobileapp.ui.devices.ACCard
import com.example.mobileapp.ui.devices.DoorCard
import com.example.mobileapp.ui.devices.LightCard
import com.example.mobileapp.ui.devices.TapCard
import com.example.mobileapp.ui.devices.VacuumCard
import com.example.mobileapp.ui.view_models.devices.AcViewModel
import com.example.mobileapp.ui.view_models.devices.DoorViewModel
import com.example.mobileapp.ui.view_models.devices.LampViewModel
import com.example.mobileapp.ui.view_models.devices.TapViewModel
import com.example.mobileapp.ui.view_models.devices.VacuumViewModel
import com.example.mobileapp.ui.views.MainScreen
import com.example.mobileapp.ui.view_models.getViewModelFactory
import com.example.mobileapp.ui.views.ConfigurationScreen
import com.example.mobileapp.ui.views.Screen


@Composable
fun AppNavGraph(navController: NavHostController) {

    val backFunc = { navController.navigate(AppDestinations.HOME.route)}
    val onDeviceSelected = { device: Device ->
        when (device.type) {
            DeviceType.LAMP -> navController.navigate(Screen.LightCard.route + "/" + device.id)
            DeviceType.AC -> navController.navigate(Screen.ACCard.route + "/" + device.id)
            DeviceType.VACUUM -> navController.navigate(Screen.VacuumCard.route + "/" + device.id)
            DeviceType.TAP -> navController.navigate(Screen.TapCard.route + "/" + device.id)
            DeviceType.DOOR -> navController.navigate(Screen.DoorCard.route + "/" + device.id)
        }
    }
    val onAddRoutine = { navController.navigate(Screen.NewRoutineScreen.route)}
    val onAddDevice = { navController.navigate(Screen.NewDeviceScreen.route)}

    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(AppDestinations.HOME.route) {
            MainScreen(onDeviceSelected, onAddDevice, onAddRoutine)
        }
        composable(AppDestinations.CONFIGURATION.route) {
            ConfigurationScreen(backFunc, {})
        }
        composable(AppDestinations.NEW_DEVICE.route) {
//            NewDeviceScreen()
        }
        composable(AppDestinations.NEW_ROUTINE.route) {
//            NewRoutineScreen()
        }

        composable(AppDestinations.LIGHT.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: LampViewModel = viewModel(factory = getViewModelFactory())
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            LightCard(vm, backFunc)
        }

        composable(AppDestinations.TAP.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: TapViewModel = viewModel(factory = getViewModelFactory())
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            TapCard(vm, backFunc)
        }

        composable(AppDestinations.VACUUM.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: VacuumViewModel = viewModel(factory = getViewModelFactory())
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            VacuumCard(vm, backFunc)
        }

        composable(AppDestinations.AC.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: AcViewModel = viewModel(factory = getViewModelFactory())
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            ACCard(vm, backFunc)
        }

        composable(AppDestinations.DOOR.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: DoorViewModel = viewModel(factory = getViewModelFactory())
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            DoorCard(vm, backFunc )
        }
    }
}