package com.example.sunrisesunset.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunrisesunset.api.Results
import com.example.sunrisesunset.SunriseSunsetApi
import kotlinx.coroutines.launch

class CalendarViewModel : ViewModel() {
    private val api = SunriseSunsetApi.create()
    var monthlySunriseSunsetData = mutableStateOf<List<Results>>(emptyList())
        private set

    fun fetchMonthlySunriseSunset(latitude: Double, longitude: Double, startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                val response = api.getSunriseSunset(latitude, longitude, startDate, endDate)
                if (response.status == "OK") {
                    monthlySunriseSunsetData.value = response.resultsList
                }
            } catch (e: Exception) {
                //
            }
        }
    }
}