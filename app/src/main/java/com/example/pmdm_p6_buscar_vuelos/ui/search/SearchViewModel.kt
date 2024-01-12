package com.example.pmdm_p6_buscar_vuelos.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmdm_p6_buscar_vuelos.data.FlightRepository
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesRepository
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val suggestionList: List<Airport> = emptyList(),
    val departureAirport: Airport = Airport(),
    val destinationList: List<Airport> = emptyList()
)

data class FavoriteUiState(
    val favoriteList: List<Favorite> = emptyList()
)

data class AirportUiState(
    val airportList: List<Airport> = emptyList()
)

class SearchViewModel(
    val flightRepository: FlightRepository,
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences.collect {
                searchFromQuery(it.searchValue)
            }
        }
    }

    val favoriteUiState: StateFlow<FavoriteUiState> =
        flightRepository.getAllFavorites()
            .map {
                FavoriteUiState(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = FavoriteUiState()
            )

    val airportUiState: StateFlow<AirportUiState> =
        flightRepository.getAllAirports()
            .map {
                AirportUiState(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = AirportUiState()
            )

    fun onQueryChanged(searchQuery: String) {
        // Actualiza preferencias del usuario
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(
                searchValue = searchQuery
            )
        }

        // Buscamos los vuelos a partir de la consulta introducida por el usuario
        searchFromQuery(searchQuery)
    }

    private fun searchFromQuery(searchQuery: String) {
        _searchUiState.update { it.copy(searchQuery = searchQuery) }

        if (searchQuery.isEmpty()) {
            viewModelScope.launch {
                _searchUiState.update {
                    searchUiState.value.copy(
                        suggestionList = emptyList()
                    )
                }
            }
        } else {
            viewModelScope.launch {
                flightRepository.getAllAirports(searchQuery).collect { airportList ->
                    _searchUiState.update {
                        searchUiState.value.copy(
                            suggestionList = airportList
                        )
                    }
                }
            }
        }
    }

    fun updateSelectedCode(selectedCode: String) {
        _searchUiState.update { it.copy(selectedCode = selectedCode) }
    }

    fun onCodeClicked(selectedCode: String) {
        viewModelScope.launch {
            val airportsList = flightRepository.getAllAirportsNoCode(selectedCode)
            _searchUiState.update {
                searchUiState.value.copy(
                    searchQuery = selectedCode,
                    selectedCode = selectedCode,
                    suggestionList = emptyList(),
                    destinationList = airportsList,
                    departureAirport = flightRepository.getAirportByCode(selectedCode)
                )
            }
        }
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(searchValue = selectedCode)
        }
    }

    fun onFavoriteClicked(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            flightRepository.getFavoriteByInfo(departureCode, destinationCode)?.let { favorite ->
                flightRepository.deleteFavorite(favorite)
            } ?: flightRepository.insertFavorite(Favorite(departureCode = departureCode, destinationCode = destinationCode))
        }
    }
}

