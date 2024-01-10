package com.example.pmdm_p6_buscar_vuelos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pmdm_p6_buscar_vuelos.ui.AppViewModelProvider

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onSelectCode: (String) -> Unit = {}
) {
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

        // Comprobamos si ha escrito algo o no en la caja de texto
        if (uiState.searchQuery.isEmpty()) {
            val favoriteList = uiState.favoriteList

            if (favoriteList.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(R.string.no_favorites_yet),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            } else {

            }
        } else {
            val airports = uiState.airportList

            AirportResults(
                airports = airports,
                onSelectCode = {
                    viewModel.updateSelectedCode(it)
                    viewModel.onSelectedCode(it)
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
                colorResource(R.color.my_light_yellow),
                shape = RoundedCornerShape(40.dp)
            )
            .clip(RoundedCornerShape(40.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.my_yellow),
                shape = RoundedCornerShape(40.dp)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}