package com.example.pmdm_p6_buscar_vuelos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R
import com.example.pmdm_p6_buscar_vuelos.model.Airport

/**
 * Función que muestra una lista de aeropuertos.
 *
 * @param modifier Modificador para personalizar el diseño de la lista.
 * @param airportList Lista de objetos Airport que representa los aeropuertos a mostrar.
 * @param onAirportClicked Función lambda que se ejecuta cuando se hace click en un aeropuerto.
 */
@Composable
fun AirportList(
    modifier: Modifier = Modifier,
    airportList: List<Airport>,
    onAirportClicked: (Airport) -> Unit = {},
) {
    // Contenedor de la lista de aeropuertos
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        items(
            items = airportList
        ) {
            // Contenedor de la información de un aeropuerto
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                // Función que muestra la información del aeropuerto
                AirportItem(
                    modifier = Modifier
                        .background(colorResource(id = R.color.my_gray_green)),
                    airport = it,
                    onAirportClicked = onAirportClicked
                )
            }
        }
    }
}

/**
 * Función que muestra la información de un aeropuerto.
 *
 * @param modifier Modificador para personalizar el diseño del elemento.
 * @param isClickable Indica si el elemento es clickeable.
 * @param airport Objeto Airport que representa el aeropuerto.
 * @param onAirportClicked Función lambda que se ejecuta cuando se hace click en el aeropuerto.
 */
@Composable
fun AirportItem(
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    airport: Airport,
    onAirportClicked: (Airport) -> Unit = {}
) {
    // Contenedor de la información del aeropuerto
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(enabled = isClickable) {
                if (airport.code != "") onAirportClicked(airport)
            }
    ) {
        // Código del aeropuerto
        Text(
            text = airport.code,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(20.dp))
        // Nombre del aeropuerto
        Text(
            text = airport.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Función para previsualizar una lista de aeropuertos.
 */
@Preview(showBackground = true)
@Composable
fun AirportListPreview() {
    AirportList(
        airportList = listOf(
            Airport(
                code = "FCO",
                name = "Leonardo da Vinci International Airport"
            ),
            Airport(
                code = "VIE",
                name = "Vienna International Airport"
            )
        )
    )
}