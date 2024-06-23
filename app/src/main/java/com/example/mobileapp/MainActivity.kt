package com.example.mobileapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.mobileapp.ui.components.AppBottomBar
import com.example.mobileapp.ui.components.AppNavigationRail
import com.example.mobileapp.ui.components.LanguagePreferences
import com.example.mobileapp.ui.navigation.AppNavGraph
import com.example.mobileapp.ui.theme.MobileAppTheme
import java.util.Locale
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage(LanguagePreferences.getLanguage(this))
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
                    if (!permissionState.status.isGranted) {
                        Text(text = "Esta aplicación necesita usar las notificaciones, por favor acepta el permiso para poder usarlas")

                        LaunchedEffect(key1 = true) {
                            permissionState.launchPermissionRequest()
                        }
                    }
                }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

                Scaffold(
                    bottomBar = {
                        if (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED) {
                            AppBottomBar(
                                currentRoute = currentRoute
                            ) { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Row(modifier = Modifier.padding(innerPadding)) {
                        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
                            AppNavigationRail(
                                currentRoute = currentRoute,
                                onNavigateToRoute = { route ->
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            AppNavGraph(navController = navController, onLanguageChange = {
                                setLanguage(it)
                                restartActivity()
                            })
                        }
                    }
                }
            }
        }
    }

    private fun setLanguage(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun restartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}