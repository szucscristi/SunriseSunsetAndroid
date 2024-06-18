package com.example.sunrisesunset.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunrisesunset.api.Results
import com.example.sunrisesunset.SunriseSunsetApi
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoViewModel : ViewModel() {
    private val api = SunriseSunsetApi.create()
    var sunriseSunsetData = mutableStateOf<Results?>(null)
        private set


    var tomorrowSunriseSunsetData = mutableStateOf<Results?>(null)
        private set

   /* var monthlySunriseSunsetData = mutableStateOf<List<Results>>(emptyList())
        private set*/


    fun fetchSunriseSunset(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = api.getSunriseSunset(latitude, longitude)
                if (response.status == "OK") {
                    sunriseSunsetData.value = response.results
                }
            } catch (e: Exception) {
                //
            }
        }
    }


    fun fetchTomorrowSunriseSunset(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val tomorrowDate = LocalDate.now().plusDays(1)
                val tomorrowDateString = tomorrowDate.format(DateTimeFormatter.ISO_DATE)
                val response = api.getSunriseSunset(latitude, longitude, tomorrowDateString)
                if (response.status == "OK") {
                    tomorrowSunriseSunsetData.value = response.results
                }
            } catch (e: Exception) {
                //
            }
        }
    }

/*
    fun fetchMonthlySunriseSunset(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val startDate = LocalDate.now()
                val endDate = startDate.plusMonths(1)
                val startDateString = startDate.format(DateTimeFormatter.ISO_DATE)
                val endDateString = endDate.format(DateTimeFormatter.ISO_DATE)
                val response = api.getSunriseSunset(latitude, longitude, startDateString, endDateString)
                if (response.status == "OK") {
                    monthlySunriseSunsetData.value = response.resultsList
                }
            } catch (e: Exception) {
                //
            }
        }
    }
*/
}
