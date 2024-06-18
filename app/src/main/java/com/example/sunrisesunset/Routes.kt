package com.example.sunrisesunset

object Routes {
    var homeScreen = "homeScreen"
    var exploreScreen = "exploreScreen"
    var settingsScreen = "settingsScreen"
    const val calendarScreen = "calendarScreen/{latitude}/{longitude}"
    const val infoScreen = "infoScreen/{latitude}/{longitude}/{country}"

    fun infoScreenRoute(latitude: Double, longitude: Double, country: String) =
        "infoScreen/$latitude/$longitude/$country"
    fun calendarScreenRoute(latitude: Double, longitude: Double) =
        "calendarScreen/$latitude/$longitude"
}