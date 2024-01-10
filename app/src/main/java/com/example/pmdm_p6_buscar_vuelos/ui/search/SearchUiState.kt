package com.example.pmdm_p6_buscar_vuelos.ui.search

import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val departureAirport: Airport = Airport(),
    val airportList: List<Airport> = emptyList(),
    val favoriteList: List<Favorite> = emptyList(),
    val destinationList: List<Airport> = emptyList(),
    val suggestionsList: List<Airport> = emptyList()
)
