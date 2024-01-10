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

    fun onChangeQuery(searchQuery: String) {
        searchFromQuery(searchQuery)
        updateQuery(searchQuery)
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
                delay(500)
            }
        } else {
            flightRepository.getAllAirports(searchQuery)
                .onEach { result ->
                    _uiState.update {
                        uiState.value.copy(
                            airportList = result,
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun updateQuery(searchQuery: String) {
        // Actualiza el valor de la consulta en el estado
        _uiState.update { it.copy(searchQuery = searchQuery) }

        // Actualiza preferencias del usuario
        updatePreferences(searchQuery)
    }

    fun onSelectedCode(selectedCode: String) {
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

    fun updateSelectedCode(selectedCode: String) {
        _uiState.update { it.copy(selectedCode = selectedCode) }
    }

    private fun updatePreferences(searchQuery: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(searchValue = searchQuery)
        }
    }

    fun onStarClick(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val favorite: Favorite = flightRepository.getFavoriteByInfo(departureCode, destinationCode)

            if (favorite == null) {
                val tmp = Favorite(
                    departureCode = departureCode,
                    destinationCode = destinationCode,
                )
                flightRepository.insertFavorite(tmp)
            } else {
                flightRepository.deleteFavorite(favorite)
            }

            val play = flightRepository.getAllFavorites()
            _uiState.update {
                uiState.value.copy(
                    favoriteList = play,
                )
            }
        }
    }
}