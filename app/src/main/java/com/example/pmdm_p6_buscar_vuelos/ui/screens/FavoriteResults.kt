package com.example.pmdm_p6_buscar_vuelos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmdm_p6_buscar_vuelos.R
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

@Composable
fun FavoriteResults(
    modifier: Modifier = Modifier,
    airportList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClick: (String, String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = stringResource(R.string.favorites_flights)
        )
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(
                favoriteList
            ) { favorite ->
                val departureAirport = airportList.first {
                        airport -> airport.code == favorite.departureCode
                }
                val destinationAirport = airportList.first {
                        airport -> airport.code == favorite.destinationCode
                }
                FlightRow(
                    departureAirport = departureAirport,
                    destinationAirport = destinationAirport,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}