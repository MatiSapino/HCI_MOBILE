package com.example.mobileapp.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.mobileapp.R

@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by rememberSaveable { mutableStateOf("") }
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun HelloContentPreview() {
    MobileAppTheme {
        HelloContent()
    }
}

enum class AppDestinations (
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int
) {
    HOME(R.string.home, Icons.Default.Home, R.string.home),
    FAVORITES(R.string.favorites, Icons.Default.Favorite, R.string.favorites),
    SHOPPING(R.string.shopping, Icons.Default.ShoppingCart, R.string.shopping),
    PROFILE(R.string.profile, Icons.Default.AccountBox, R.string.profile)
}

@Composable
fun NavigationScaffold() {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    adaptiveInfo.windowSizeClass.windowWidthSizeClass
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationRail
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = { Text(stringResource(it.label)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        },
        layoutType = customNavSuiteType,
    ) {
        when (currentDestination) {
            AppDestinations.HOME -> HomeDestination()
            AppDestinations.FAVORITES -> FavoritesDestination()
            AppDestinations.SHOPPING -> ShoppingDestination()
            AppDestinations.PROFILE -> ProfileDestination()
        }
    }
}

@Composable
fun HomeDestination() {
    Text(text = "home")
}

@Composable
fun FavoritesDestination() {
    Text(text = "favorites")
}

@Composable
fun ShoppingDestination() {
    Text(text = "shopping")
}

@Composable
fun ProfileDestination() {
    Text(text = "profile")
}

@Preview(showBackground = true)
@Composable
fun NavigationScaffoldPreview(){
    NavigationScaffold()
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun NavigationScaffoldTabletPreview(){
    NavigationScaffold()
}