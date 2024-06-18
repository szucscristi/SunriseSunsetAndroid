package com.example.sunrisesunset.models

import SettingsRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsRepository = SettingsRepository(application)
    val isDarkTheme: StateFlow<Boolean> = settingsRepository.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleTheme() {
        viewModelScope.launch {
            settingsRepository.saveThemeSetting(!isDarkTheme.value)
        }
    }
}
