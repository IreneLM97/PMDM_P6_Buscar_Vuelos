package com.example.pmdm_p6_buscar_vuelos.ui.search

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

@Composable
fun FavoriteList(
    modifier: Modifier = Modifier,
    airportList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClicked: (String, String) -> Unit,
    onSendButtonClicked: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .padding(top = 20.dp),
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
                FlightItem(
                    departureAirport = departureAirport,
                    destinationAirport = destinationAirport,
                    onFavoriteClicked = onFavoriteClicked,
                    onSendButtonClicked = onSendButtonClicked
                )
            }
        }
    }
}

@Composable
fun NoFavoritesResult() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .wrapContentSize(align = Alignment.TopCenter)
    ) {
        Text(
            text = stringResource(R.string.no_favorites_yet),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}