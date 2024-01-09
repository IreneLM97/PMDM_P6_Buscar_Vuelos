package com.example.pmdm_p6_buscar_vuelos.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pmdm_p6_buscar_vuelos.FlightApplication
import com.example.pmdm_p6_buscar_vuelos.ui.screens.SearchViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)
            val flightRepository = application.container.flightRepository
            val preferencesRepository = application.userPreferencesRepository
            SearchViewModel(
                flightRepository = flightRepository,
                userPreferencesRepository = preferencesRepository
            )
        }
    }
}