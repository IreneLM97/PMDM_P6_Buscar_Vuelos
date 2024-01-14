package com.example.pmdm_p6_buscar_vuelos.ui.screen

import com.example.pmdm_p6_buscar_vuelos.model.Airport

/**
 * Clase de estado que representa el estado actual de la pantalla de búsqueda.
 *
 * @property searchQuery Consulta de búsqueda actual.
 * @property suggestionList Lista de sugerencias de aeropuertos.
 * @property selectedAirport Aeropuerto seleccionado.
 */
data class SearchUiState(
    val searchQuery: String = "",
    val suggestionList: List<Airport> = emptyList(),
    val selectedAirport: Airport? = null
)