package com.example.pmdm_p6_buscar_vuelos.ui.screens

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmdm_p6_buscar_vuelos.data.FlightRepository
import com.example.pmdm_p6_buscar_vuelos.data.UserPreferencesRepository
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

    fun onQueryChange(searchQuery: String) {
        searchFromQuery(searchQuery)
        updateQuery(searchQuery)
    }

    private fun searchFromQuery(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            viewModelScope.launch {
                _uiState.update {
                    uiState.value.copy(
                        favoriteList = flightRepository.getAllFavorites().toMutableStateList()
                    )
                }
            }
        } else {
            flightRepository.getAllAirports(searchQuery)
                // on each emission
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
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(searchValue = searchQuery)
        }
    }

    fun updateSelectedCode(selectedCode: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCode = selectedCode,
            )
        }
    }
}