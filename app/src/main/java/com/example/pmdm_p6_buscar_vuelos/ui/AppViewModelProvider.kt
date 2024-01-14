package com.example.pmdm_p6_buscar_vuelos.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pmdm_p6_buscar_vuelos.FlightApplication
import com.example.pmdm_p6_buscar_vuelos.ui.screen.SearchViewModel

/**
 * Objeto singleton que proporciona un [ViewModelProvider.Factory] para crear instancias de [SearchViewModel].
 */
object AppViewModelProvider {
    /**
     * Fábrica que crea instancias de [SearchViewModel].
     */
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            // Obtener la instancia de la aplicación
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)

            // Obtener los repositorios necesarios desde el contenedor de la aplicación
            val flightRepository = application.container.flightRepository
            val preferencesRepository = application.userPreferencesRepository

            // Crear e inicializar la instancia de SearchViewModel con los repositorios
            SearchViewModel(
                flightRepository = flightRepository,
                userPreferencesRepository = preferencesRepository
            )
        }
    }
}