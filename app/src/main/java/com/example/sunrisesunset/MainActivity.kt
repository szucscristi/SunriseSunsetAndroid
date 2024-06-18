package com.example.sunrisesunset

import CalendarScreen
import HomeScreen
import InfoScreen
import SettingsRepository
import SettingsScreen
import com.example.sunrisesunset.models.ThemeViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sunrisesunset.models.InfoViewModel
import com.example.sunrisesunset.screens.ExploreScreen
import com.example.sunrisesunset.ui.theme.SunriseSunsetTheme
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                locationPermissionGranted = true
            } else {
                locationPermissionGranted = false
            }
        }

    private var locationPermissionGranted by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val settingsRepository = SettingsRepository(applicationContext)

            LaunchedEffect(Unit) {
                settingsRepository.locationPermission.collect { isGranted ->
                    locationPermissionGranted = isGranted
                }
            }

            SunriseSunsetTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items = listOf(
                        BottomNavigationItem(
                            title = "Home",
                            route = Routes.homeScreen,
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home
                        ),
                        BottomNavigationItem(
                            title = "My location",
                            route = Routes.infoScreen,
                            selectedIcon = Icons.Filled.LocationOn,
                            unselectedIcon = Icons.Outlined.LocationOn
                        ),
                        BottomNavigationItem(
                            title = "Explore",
                            route = Routes.exploreScreen,
                            selectedIcon = Icons.Filled.Info,
                            unselectedIcon = Icons.Outlined.Info
                        ),
                        BottomNavigationItem(
                            title = "Settings",
                            route = Routes.settingsScreen,
                            selectedIcon = Icons.Filled.Settings,
                            unselectedIcon = Icons.Outlined.Settings
                        ),
                    )

                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            when (index) {
                                                0,2,3 -> {
                                                    navController.navigate(item.route) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            inclusive = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                                1 -> fetchLocation(navController)
                                                else -> {
                                                    navController.navigate(item.route) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }

                                            }
                                        },
                                        label = { Text(text = item.title) },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Routes.homeScreen,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Routes.homeScreen) {
                                HomeScreen(navController = navController)
                            }
                            composable(Routes.exploreScreen) {
                                ExploreScreen(
                                    navController = navController,
                                    context = this@MainActivity
                                )
                            }
                            composable(Routes.settingsScreen) {
                                SettingsScreen(
                                    themeViewModel = themeViewModel,
                                    locationPermission = locationPermissionGranted,
                                    onLocationPermissionToggle = {
                                        if (it) {
                                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                        } else {
                                            locationPermissionGranted = false
                                            themeViewModel.viewModelScope.launch {
                                                settingsRepository.saveLocationPermission(false)
                                            }
                                        }
                                    },
                                )
                            }
                            composable(
                                route = Routes.infoScreen,
                                arguments = listOf(
                                    navArgument("latitude") { type = NavType.StringType },
                                    navArgument("longitude") { type = NavType.StringType },
                                    navArgument("country") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val latitude =
                                    backStackEntry.arguments?.getString("latitude") ?: "0.0"
                                val longitude =
                                    backStackEntry.arguments?.getString("longitude") ?: "0.0"
                                val country = backStackEntry.arguments?.getString("country") ?: ""
                                val infoViewModel: InfoViewModel = viewModel()
                                InfoScreen(
                                    navController = navController,
                                    latitude = latitude,
                                    longitude = longitude,
                                    country = country,
                                    infoViewModel = infoViewModel
                                )
                            }
                            composable(
                                route = Routes.calendarScreen,
                                arguments = listOf(
                                    navArgument("latitude") { type = NavType.StringType },
                                    navArgument("longitude") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val latitude =
                                    backStackEntry.arguments?.getString("latitude") ?: "0.0"
                                val longitude =
                                    backStackEntry.arguments?.getString("longitude") ?: "0.0"
                                CalendarScreen(
                                    latitude = latitude,
                                    longitude = longitude,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchLocation(navController: NavController) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val latitude = it.latitude
                        val longitude = it.longitude
                        navController.navigate(Routes.infoScreenRoute(latitude, longitude, "your location"))
                    }
                }
        } else {

            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
