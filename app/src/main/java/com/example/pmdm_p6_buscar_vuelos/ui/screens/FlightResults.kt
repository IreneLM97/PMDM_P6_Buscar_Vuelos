package com.example.pmdm_p6_buscar_vuelos.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FlightRow(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    departureAirport: Airport,
    destinationAirport: Airport,
    onFavoriteClick: (String, String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            Column(
                modifier = modifier
                    .weight(10f)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.depart_label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    AirportRow(
                        isClickable = false,
                        airport = departureAirport
                    )
                    Text(
                        text = stringResource(R.string.arrive_label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    AirportRow(
                        isClickable = false,
                        airport = destinationAirport
                    )
                }
            }
            IconButton(
                onClick = {
                    onFavoriteClick(departureAirport.code, destinationAirport.code)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    tint = if (isFavorite) colorResource(id = R.color.my_golden_yellow) else Color.LightGray,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun FlightResults(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 140.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            text = stringResource(
                id = R.string.flights_from,
                departureAirport.code
            )
        )
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(
                items = destinationList
            ) { item ->
                val isFavorite = favoriteList.find {
                    it.departureCode == departureAirport.code && it.destinationCode == item.code
                }

                FlightRow(
                    isFavorite = isFavorite != null,
                    departureAirport = departureAirport,
                    destinationAirport = item,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Preview
@Composable
fun FlightRowPreview() {
    FlightRow(
        isFavorite = true,
        departureAirport = Airport(
            code = "FCO",
            name = "Leonardo da Vinci International Airport"
        ),
        destinationAirport = Airport(
            code = "VIE",
            name = "Vienna International Airport"
        ),
        onFavoriteClick = { _, _ -> }
    )
}

@Preview
@Composable
fun FlightResultsPreview() {
    FlightResults(
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
        onFavoriteClick = { _, _ -> }
    )
}