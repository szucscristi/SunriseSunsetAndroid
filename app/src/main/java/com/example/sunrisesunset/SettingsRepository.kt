import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit

import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object SettingsPreferences {
    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    val LOCATION_PERMISSION = booleanPreferencesKey("location_permission")
}

class SettingsRepository(private val context: Context) {

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SettingsPreferences.IS_DARK_THEME] ?: false
        }

    val locationPermission: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SettingsPreferences.LOCATION_PERMISSION] ?: false
        }

    suspend fun saveThemeSetting(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SettingsPreferences.IS_DARK_THEME] = isDarkTheme
        }
    }

    suspend fun saveLocationPermission(isGranted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SettingsPreferences.LOCATION_PERMISSION] = isGranted
        }
    }
}
