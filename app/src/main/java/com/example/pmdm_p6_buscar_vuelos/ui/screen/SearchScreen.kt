package com.example.pmdm_p6_buscar_vuelos.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.ui.AppViewModelProvider
import com.example.pmdm_p6_buscar_vuelos.ui.components.AirportList
import com.example.pmdm_p6_buscar_vuelos.ui.components.FavoriteList
import com.example.pmdm_p6_buscar_vuelos.ui.components.FlightList
import com.example.pmdm_p6_buscar_vuelos.ui.components.LoadingIcon
import com.example.pmdm_p6_buscar_vuelos.ui.components.NoFavoriteList
import com.example.pmdm_p6_buscar_vuelos.ui.components.SearchTextField

/**
 * Función que muestra la pantalla de búsqueda con los resultados de búsqueda.
 *
 * @param modifier Modificador para personalizar el diseño de la pantalla.
 * @param onQueryChanged Función lambda que se ejecuta cuando cambia la consulta de búsqueda.
 * @param onAirportClicked Función lambda que se ejecuta cuando se hace click en una sugerencia.
 * @param onFavoriteClicked Función lambda que se ejecuta cuando se hace click en un icono de favorito.
 * @param onShareButtonClicked Función lambda que se ejecuta cuando se hace click en el icono de compartir.
 * @param viewModel ViewModel utilizado para gestionar el estado de la búsqueda.
 */
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit = {},
    onAirportClicked: (Airport) -> Unit = {},
    onFavoriteClicked: (String, String) -> Unit = {_,_ -> },
    onShareButtonClicked: (String) -> Unit = {},
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Obtenemos el estado actual de la búsqueda y el flujo de datos de favoritos y aeropuertos
    val searchUiState = viewModel.searchUiState.collectAsState().value
    val favoriteList by viewModel.favoriteList.collectAsState()
    val airportList by viewModel.airportList.collectAsState()

    // Contenedor de los elementos de la pantalla
    Column(
        modifier = modifier
    ) {
        // Caja de texto de búsqueda
        SearchTextField(
            searchQuery = searchUiState.searchQuery,
            onQueryChanged = onQueryChanged
        )

        // Mostramos los resultados de la búsqueda en función del estado de la aplicación
        when {
            // La pantalla está en estado de carga -> Icono de cargando
            searchUiState.isLoading -> {
                LoadingIcon()
            }
            // No hay texto de búsqueda y no hay favoritos -> Mensaje de no favoritos
            searchUiState.searchQuery.isEmpty() && favoriteList.isEmpty() -> {
                NoFavoriteList()
            }
            // No hay texto de búsqueda y si hay favoritos -> Lista de favoritos
            searchUiState.searchQuery.isEmpty() -> {
                FavoriteList(
                    airportList = airportList,
                    favoriteList = favoriteList,
                    onFavoriteClicked = onFavoriteClicked,
                    onShareButtonClicked = onShareButtonClicked
                )
            }
            // Se ha seleccionado algún aeropuerto -> Lista de vuelos con ese aeropuerto de salida
            searchUiState.selectedAirport != null -> {
                FlightList(
                    departureAirport = searchUiState.selectedAirport,
                    destinationList = airportList.filterNot { it.code == searchUiState.selectedAirport.code },
                    favoriteList = favoriteList,
                    onFavoriteClicked = onFavoriteClicked,
                    onShareButtonClicked = onShareButtonClicked
                )
            }
            // En cualquier otro caso -> Lista de sugerencias de aeropuertos según el texto de búsqueda
            else -> {
                AirportList(
                    airportList = searchUiState.suggestionList,
                    onAirportClicked = onAirportClicked
                )
            }
        }
    }
}

/**
 * Función para previsualizar la pantalla de búsqueda.
 */
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}