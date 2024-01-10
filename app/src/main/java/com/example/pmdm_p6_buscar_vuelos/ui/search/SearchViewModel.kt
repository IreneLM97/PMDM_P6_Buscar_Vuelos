package com.example.pmdm_p6_buscar_vuelos.ui.search

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmdm_p6_buscar_vuelos.data.FlightRepository
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesRepository
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    val flightRepository: FlightRepository,
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences.collect {
                searchFromQuery(it.searchValue)
            }
        }
    }

    fun onQueryChanged(searchQuery: String) {
        // Actualiza preferencias del usuario
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(searchValue = searchQuery)
        }

        // Buscamos los vuelos a partir de la consulta introducida por el usuario
        searchFromQuery(searchQuery)
    }

    private fun searchFromQuery(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }

        if (searchQuery.isEmpty()) {
            viewModelScope.launch {
                _uiState.update {
                    uiState.value.copy(
                        airportList = flightRepository.getAllAirports().toMutableStateList(),
                        favoriteList = flightRepository.getAllFavorites().toMutableStateList()
                    )
                }
                delay(1000)
            }
        } else {
            viewModelScope.launch {
                flightRepository.getAllAirports(searchQuery).collect { airportList ->
                        _uiState.update {
                            uiState.value.copy(
                                airportList = airportList,
                            )
                        }
                    }
            }
        }
    }

    fun updateSelectedCode(selectedCode: String) {
        _uiState.update { it.copy(selectedCode = selectedCode) }
    }

    fun onCodeClicked(selectedCode: String) {
        viewModelScope.launch {
            val favoritesList = flightRepository.getAllFavorites().toMutableStateList()
            val airportsList = flightRepository.getAllAirportsNoCode(selectedCode)
            _uiState.update {
                uiState.value.copy(
                    searchQuery = selectedCode,
                    selectedCode = selectedCode,
                    airportList = emptyList(),
                    favoriteList = favoritesList,
                    destinationList = airportsList,
                    departureAirport = flightRepository.getAirportByCode(selectedCode)
                )
            }
        }
    }

    fun onFavoriteClicked(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            flightRepository.getFavoriteByInfo(departureCode, destinationCode)?.let { favorite ->
                flightRepository.deleteFavorite(favorite)
            } ?: flightRepository.insertFavorite(Favorite(departureCode = departureCode, destinationCode = destinationCode))

            _uiState.update {
                uiState.value.copy(
                    favoriteList = flightRepository.getAllFavorites(),
                )
            }
        }
    }
}