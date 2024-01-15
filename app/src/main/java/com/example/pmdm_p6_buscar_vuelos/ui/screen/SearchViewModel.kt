package com.example.pmdm_p6_buscar_vuelos.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmdm_p6_buscar_vuelos.data.room.FlightRepository
import com.example.pmdm_p6_buscar_vuelos.data.datastore.UserPreferencesRepository
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * [ViewModel] para gestionar el estado y la lógica de la pantalla de búsqueda.
 *
 * @param flightRepository Repositorio para acceder a las operaciones con la base de datos.
 * @param userPreferencesRepository Repositorio para acceder a las preferencias del usuario.
 */
class SearchViewModel(
    val flightRepository: FlightRepository,
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Estado de la interfaz de usuario relacionado con la búsqueda
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    // Flujo de datos para la lista de favoritos
    val favoriteList: StateFlow<List<Favorite>> =
        flightRepository.getAllFavorites()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    // Flujo de datos para la lista de aeropuertos
    val airportList: StateFlow<List<Airport>> =
        flightRepository.getAllAirports()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    // Inicialización del ViewModel
    init {
        viewModelScope.launch {
            // Observamos los cambios en las preferencias del usuario
            userPreferencesRepository.userPreferences.collect {
                searchFromQuery(it.searchValue)
                // Comprobamos si se han cargado los datos
                checkLoadingStatus()
            }
        }
    }

    /**
     * Función para verificar si se ha cargado la información de la base de datos y actualizar isLoading.
     */
    private suspend fun checkLoadingStatus() {
        delay(500)  // delay para mostrar icono de cargando
        _searchUiState.update { it.copy(isLoading = airportList.value.isEmpty()) }
    }

    /**
     * Función que se ejecuta cuando cambia la consulta de búsqueda.
     *
     * @param searchQuery Consulta de búsqueda actual.
     */
    fun onQueryChanged(searchQuery: String) {
        // Actualizamos preferencias del usuario
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(searchValue = searchQuery)
        }

        // Realizamos búsqueda de aeropuertos a partir de la consulta
        searchFromQuery(searchQuery)
    }

    /**
     * Función para realizar una búsqueda a partir de la consulta.
     *
     * @param searchQuery Consulta de búsqueda actual.
     */
    private fun searchFromQuery(searchQuery: String) {
        // Actualizamos el texto de búsqueda
        _searchUiState.update { it.copy(searchQuery = searchQuery) }

        // Actualizamos la lista de sugerencias
        viewModelScope.launch {
            if (searchQuery.isEmpty()) {
                _searchUiState.update { it.copy(suggestionList = emptyList()) }
            } else {
                flightRepository.getAllAirports(searchQuery).collect { airportList ->
                    _searchUiState.update { it.copy(suggestionList = airportList) }
                }
            }
        }
    }

    /**
     * Función para actualizar el aeropuerto seleccionado.
     *
     * @param selectedAirport Aeropuerto seleccionado.
     */
    fun updateSelectedAirport(selectedAirport: Airport?) {
        _searchUiState.update { it.copy(selectedAirport = selectedAirport) }
    }

    /**
     * Función que se ejecuta cuando se hace click en un aeropuerto.
     *
     * @param selectedAirport Aeropuerto seleccionado.
     */
    fun onAirportClicked(selectedAirport: Airport) {
        viewModelScope.launch {
            // Actualizamos el estado de la búsqueda
            // NOTA. Se actualiza el texto de búsqueda al código del aeropuerto seleccionado
            _searchUiState.update {
                searchUiState.value.copy(
                    searchQuery = selectedAirport.code,
                    selectedAirport = selectedAirport,
                    suggestionList = emptyList()
                )
            }

            // Actualizamos las preferencias del usuario
            userPreferencesRepository.saveUserPreferences(searchValue = selectedAirport.code)
        }
    }

    /**
     * Función que se ejecuta cuando se hace click en un icono de favorito.
     *
     * @param departureCode Código del aeropuerto de salida.
     * @param destinationCode Código del aeropuerto de llegada.
     */
    fun onFavoriteClicked(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            // Si ese vuelo era favorito -> Se elimina el favorito
            // Si ese vuelo no era favorito (get devuelve null) -> Se inserta el favorito
            flightRepository.getFavoriteByInfo(departureCode, destinationCode)?.let { favorite ->
                flightRepository.deleteFavorite(favorite)
            } ?: flightRepository.insertFavorite(Favorite(departureCode = departureCode, destinationCode = destinationCode))
        }
    }
}

