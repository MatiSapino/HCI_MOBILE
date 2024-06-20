package com.example.mobileapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
//            ConfigurationScreen()
        }
        composable(AppDestinations.NEW_DEVICE.route) {
//            NewDeviceScreen()
        }
        composable(AppDestinations.NEW_ROUTINE.route) {
//            NewRoutineScreen()
        }

        composable(AppDestinations.LIGHT.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: LampViewModel = viewModel()
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            LightCard(vm, { navController.navigate(AppDestinations.HOME.route)})
        }

        composable(AppDestinations.TAP.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: TapViewModel = viewModel()
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            TapCard(vm, { navController.navigate(AppDestinations.HOME.route)})
        }

        composable(AppDestinations.VACUUM.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: VacuumViewModel = viewModel()
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            VacuumCard(vm, { navController.navigate(AppDestinations.HOME.route)})
        }

        composable(AppDestinations.AC.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: AcViewModel = viewModel()
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            ACCard(vm, { navController.navigate(AppDestinations.HOME.route)})
        }

        composable(AppDestinations.DOOR.route, arguments = listOf(navArgument("deviceId") {type = NavType.StringType})) {
            backStackEntry ->
            val vm: DoorViewModel = viewModel()
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            vm.setCurrentDevice(deviceId!!)
            DoorCard(vm, { navController.navigate(AppDestinations.HOME.route)} )
        }
    }
}