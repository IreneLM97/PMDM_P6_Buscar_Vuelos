package com.example.pmdm_p6_buscar_vuelos.ui.screens

import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val airportList: List<Airport> = emptyList(),
    val destinationList: List<Airport> = emptyList(),
    val favoriteList: List<Favorite> = emptyList()
)
