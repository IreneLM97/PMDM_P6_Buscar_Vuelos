package com.example.pmdm_p6_buscar_vuelos.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchUiState: SearchUiState = SearchUiState(),
    onQueryChanged: (String) -> Unit = {},
    onCodeClicked: (String) -> Unit = {},
    onFavoriteClicked: (String, String) -> Unit = {_,_ -> },
    onSendButtonClicked: (String) -> Unit = {}
) {
    // Columna principal que contiene los elementos de la pantalla
    Column(
        modifier = modifier
    ) {
        // Caja de texto de búsqueda
        SearchTextField(
            searchQuery = searchUiState.searchQuery,
            onQueryChanged = onQueryChanged
        )

        // Mostramos los resultados en función del estado de la aplicación
        when {
            searchUiState.searchQuery.isEmpty() && searchUiState.favoriteList.isEmpty() -> {
                NoFavoritesResult()
            }
            searchUiState.searchQuery.isEmpty() -> {
                FavoriteResults(
                    airportList = searchUiState.airportList,
                    favoriteList = searchUiState.favoriteList,
                    onFavoriteClicked = onFavoriteClicked,
                    onSendButtonClicked = onSendButtonClicked
                )
            }
            searchUiState.selectedCode.isNotEmpty() -> {
                FlightResults(
                    departureAirport = searchUiState.departureAirport,
                    destinationList = searchUiState.destinationList,
                    favoriteList = searchUiState.favoriteList,
                    onFavoriteClick = onFavoriteClicked,
                    onSendButtonClicked = onSendButtonClicked
                )
            }
            else -> {
                AirportResults(
                    airports = searchUiState.airportList,
                    onCodeClicked = onCodeClicked
                )
            }
        }
    }
}

@Composable
fun SearchTextField(
    searchQuery: String,
    onQueryChanged: (String) -> Unit
) {
    // Campo de texto para ingresar la consulta de búsqueda
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onQueryChanged(it) },
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(text = stringResource(R.string.search_label))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 15.dp, top = 8.dp)
            .background(
                colorResource(R.color.my_light_green),
                shape = RoundedCornerShape(40.dp)
            )
            .clip(RoundedCornerShape(40.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.my_dark_green),
                shape = RoundedCornerShape(40.dp)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}