package com.example.pmdm_p6_buscar_vuelos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Función que muestra una lista de vuelos desde un aeropuerto de salida.
 *
 * @param modifier Modificador para personalizar el diseño de la lista.
 * @param departureAirport Aeropuerto de salida.
 * @param destinationList Lista de aeropuertos de llegada.
 * @param favoriteList Lista de favoritos.
 * @param onFavoriteClicked Función lambda que se ejecuta cuando se hace click en el icono de favorito.
 * @param onShareButtonClicked Función lambda que se ejecuta cuando se hace click en el icono de compartir.
 */
@Composable
fun FlightList(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClicked: (String, String) -> Unit,
    onShareButtonClicked: (String) -> Unit
) {
    // Contenedor de la lista de vuelos
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        // Título de vuelos con código de aeropuerto de salida
        Text(
            modifier = Modifier
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = stringResource(
                id = R.string.flights_from,
                departureAirport.code
            )
        )
        // Lista de vuelos de destino
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(
                items = destinationList
            ) { item ->
                // Comprobamos si es un vuelo marcado como favorito o no
                val isFavorite = favoriteList.find {
                    it.departureCode == departureAirport.code && it.destinationCode == item.code
                }

                // Mostramos información del vuelo
                FlightItem(
                    isFavorite = isFavorite != null,
                    departureAirport = departureAirport,
                    destinationAirport = item,
                    onFavoriteClicked = onFavoriteClicked,
                    onShareButtonClicked = onShareButtonClicked
                )
            }
        }
    }
}

/**
 * Función que muestra la información de un vuelo con detalles y opciones.
 *
 * @param modifier Modificador para personalizar el diseño del elemento.
 * @param isFavorite Indica si el vuelo es un favorito.
 * @param departureAirport Aeropuerto de salida.
 * @param destinationAirport Aeropuerto de llegada.
 * @param onFavoriteClicked Función lambda que se ejecuta cuando se hace click en el ícono de favorito.
 * @param onShareButtonClicked Función lambda que se ejecuta cuando se hace click en el ícono de compartir.
 */
@Composable
fun FlightItem(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = true,
    departureAirport: Airport,
    destinationAirport: Airport,
    onFavoriteClicked: (String, String) -> Unit,
    onShareButtonClicked: (String) -> Unit
) {
    // Resumen de la información del vuelo
    val flightSummary = stringResource(
        R.string.flight_summary,
        departureAirport.name,
        destinationAirport.name,
    )

    // Contenedor de la información del vuelo
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.my_gray_dark_green))
        ) {
            Column(
                modifier = modifier
                    .weight(10f)
            ) {
                // Mensaje de salida
                Text(
                    text = stringResource(R.string.depart_label),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 32.dp)
                )
                // Información del aeropuerto de salida
                AirportItem(
                    isClickable = false,
                    airport = departureAirport
                )
                // Mensaje de llegada
                Text(
                    text = stringResource(R.string.arrive_label),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 32.dp)
                )
                // Información del aeropuerto de llegada
                AirportItem(
                    isClickable = false,
                    airport = destinationAirport
                )
            }

            // Botón del icono de favorito
            IconButton(
                onClick = {
                    onFavoriteClicked(departureAirport.code, destinationAirport.code)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                // Icono de favorito
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    tint = if (isFavorite) colorResource(id = R.color.my_golden_yellow) else Color.Gray,
                    contentDescription = null
                )
            }

            // Botón del icono de compartir
            IconButton(
                onClick = { onShareButtonClicked(flightSummary) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                // Icono de compartir
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.Share,
                    tint = Color.Gray,
                    contentDescription = null
                )
            }
        }
    }
}

/**
 * Función para previsualizar una lista de vuelos.
 */
@Preview
@Composable
fun FlightListPreview() {
    FlightList(
        departureAirport = Airport(
            code = "FCO",
            name = "Leonardo da Vinci International Airport"
        ),
        destinationList = listOf(
            Airport(
                code = "VIE",
                name = "Vienna International Airport"
            ),
            Airport(
                code = "MUC",
                name = "Munich International Airport"
            ),
            Airport(
                code = "ATH",
                name = "Athens International Airport"
            )
        ),
        favoriteList = listOf(
            Favorite(
                departureCode = "FCO",
                destinationCode = "VIE"
            ),
            Favorite(
                departureCode = "FCO",
                destinationCode = "ATH"
            )
        ),
        onFavoriteClicked = { _, _ -> },
        onShareButtonClicked = {}
    )
}