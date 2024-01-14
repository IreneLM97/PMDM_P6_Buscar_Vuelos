package com.example.pmdm_p6_buscar_vuelos.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmdm_p6_buscar_vuelos.R
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

/**
 * Función que muestra la lista de favoritos con los detalles de los vuelos.
 *
 * @param modifier Modificador para personalizar el diseño de la lista.
 * @param airportList Lista de aeropuertos.
 * @param favoriteList Lista de favoritos.
 * @param onFavoriteClicked Función lambda que se ejecuta cuando se hace click en el icono de favorito.
 * @param onShareButtonClicked Función lambda que se ejecuta cuando se hace click en el icono de compartir.
 */
@Composable
fun FavoriteList(
    modifier: Modifier = Modifier,
    airportList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClicked: (String, String) -> Unit,
    onShareButtonClicked: (String) -> Unit
) {
    // Contenedor de la lista de favoritos
    Column {
        // Título de favoritos
        Text(
            modifier = Modifier
                .padding(10.dp)
                .padding(top = 20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = stringResource(R.string.favorites_flights)
        )
        // Lista de favoritos
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(
                favoriteList
            ) { favorite ->
                // Obtenemos aeropuerto de salida del favorito
                val departureAirport = airportList.first {
                        airport -> airport.code == favorite.departureCode
                }
                // Obtenemos aeropuerto de llegada del favorito
                val destinationAirport = airportList.first {
                        airport -> airport.code == favorite.destinationCode
                }

                // Mostramos información del vuelo
                FlightItem(
                    departureAirport = departureAirport,
                    destinationAirport = destinationAirport,
                    onFavoriteClicked = onFavoriteClicked,
                    onShareButtonClicked = onShareButtonClicked
                )
            }
        }
    }
}

/**
 * Función que muestra un mensaje cuando no hay favoritos.
 */
@Composable
fun NoFavoriteList() {
    // Contenedor del mensaje
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .wrapContentSize(align = Alignment.TopCenter)
    ) {
        // Mensaje
        Text(
            text = stringResource(R.string.no_favorites_yet),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}