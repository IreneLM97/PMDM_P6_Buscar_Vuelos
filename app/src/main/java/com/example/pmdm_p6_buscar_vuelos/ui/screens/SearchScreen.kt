package com.example.pmdm_p6_buscar_vuelos.ui.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pmdm_p6_buscar_vuelos.ui.AppViewModelProvider

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    val viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState().value

    // Columna principal que contiene los elementos de la pantalla
    Column(
        modifier = modifier
    ) {
        // Caja de texto de búsqueda
        SearchTextField(
            uiState.searchQuery,
            onQueryChange = {
                viewModel.updateSelectedCode("")
                viewModel.onChangeQuery(it)
            }
        )

        val airportList = uiState.airportList
        val favoriteList = uiState.favoriteList
        val searchQuery = uiState.searchQuery

        // Comprobamos si ha escrito algo o no en la caja de texto
        when {
            searchQuery.isEmpty() && favoriteList.isEmpty() -> {
                NoFavoritesResult()
            }
            searchQuery.isEmpty() -> {
                FavoriteResults(
                    airportList = airportList,
                    favoriteList = favoriteList,
                    onFavoriteClick = { _, _ -> }
                )
            }
            else -> {
                AirportResults(
                    airports = airportList,
                    onSelectCode = {
                        viewModel.updateSelectedCode(it)
                        viewModel.onSelectedCode(it)
                        focusManager.clearFocus()
                    }
                )
            }
        }

        // Mostramos los vuelos en función del código seleccionado
        if(uiState.selectedCode.isNotEmpty()) {
            FlightResults(
                departureAirport = uiState.departureAirport,
                destinationList = uiState.destinationList,
                favoriteList = uiState.favoriteList,
                onFavoriteClick = {_,_ -> }
            )
        }
    }
}

@Composable
fun SearchTextField(
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    // Campo de texto para ingresar la consulta de búsqueda
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onQueryChange(it) },
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(text = stringResource(R.string.search_label))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
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