package com.example.pmdm_p6_buscar_vuelos.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesKeys.SEARCH_VALUE
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesKeys.TAG
import kotlinx.coroutines.flow.*
import java.io.IOException

data class UserPreferences(
    val searchValue: String = "",
)

object UserPreferencesKeys {
    const val TAG = "UserPreferencesRepo"
    val SEARCH_VALUE = stringPreferencesKey("search_value")
}

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveUserPreferences(
        searchValue: String,
    ) {
        dataStore.edit { preferences ->
            preferences[SEARCH_VALUE] = searchValue
        }
    }

    val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            UserPreferences(
                searchValue = preferences[SEARCH_VALUE] ?: "",
            )
        }
}