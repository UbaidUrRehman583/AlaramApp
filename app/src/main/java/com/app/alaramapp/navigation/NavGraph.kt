package com.example.alarmapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.app.alaramapp.screens.addalarm.AlarmScreenNavigation
import com.app.alaramapp.screens.home.HomeScreenNavigation
import com.app.alaramapp.screens.setting.SettingNavigation
import com.app.alaramapp.screens.setting.SettingsScreen

object Routes {
    const val HOME = "home"
    const val ADD_ALARM = "add_alarm"
    const val SETTINGS = "settings"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val showBottomBar = currentDestination?.route in listOf(Routes.HOME, Routes.SETTINGS)
    val showBackIcon = currentDestination?.route == Routes.ADD_ALARM

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentDestination?.route) {
                            Routes.HOME -> "Alarms"
                            Routes.ADD_ALARM -> "Add Alarm"
                            Routes.SETTINGS -> "Settings"
                            else -> ""
                        }
                    )
                },
                navigationIcon = {
                    if (showBackIcon) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreenNavigation(
                    onAddAlarmClick = { navController.navigate(Routes.ADD_ALARM) }
                )
            }
            composable(Routes.ADD_ALARM) {
                AlarmScreenNavigation(
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.SETTINGS) {
                SettingNavigation()
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.HOME } == true,
            onClick = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.SETTINGS } == true,
            onClick = {
                navController.navigate(Routes.SETTINGS) {
                    popUpTo(Routes.HOME)
                    launchSingleTop = true
                }
            }
        )
    }
}
