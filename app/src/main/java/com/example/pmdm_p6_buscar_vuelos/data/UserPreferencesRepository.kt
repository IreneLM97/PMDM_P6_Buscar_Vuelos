package com.example.pmdm_p6_buscar_vuelos.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesKeys.SEARCH_VALUE
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesKeys.TAG
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * Clase de datos que representa las preferencias del usuario.
 *
 * @param searchValue Valor de búsqueda almacenado como preferencia del usuario.
 */
data class UserPreferences(
    val searchValue: String = "",
)

/**
 * Objeto que contiene claves para las preferencias del usuario.
 */
object UserPreferencesKeys {
    const val TAG = "UserPreferencesRepo"
    val SEARCH_VALUE = stringPreferencesKey("search_value")
}

/**
 * Clase que gestiona las preferencias del usuario utilizando DataStore.
 *
 * @param dataStore Almacén de datos para las preferencias del usuario.
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    /**
     * Guarda las preferencias del usuario, en este caso, el valor de búsqueda.
     *
     * @param searchValue Valor de búsqueda.
     */
    suspend fun saveUserPreferences(searchValue: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_VALUE] = searchValue
        }
    }

    /**
     * Flujo que emite las preferencias del usuario y maneja errores, como la falta de acceso a las preferencias.
     */
    val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
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