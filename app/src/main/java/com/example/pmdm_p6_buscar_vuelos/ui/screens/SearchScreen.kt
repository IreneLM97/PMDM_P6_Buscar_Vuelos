package com.example.pmdm_p6_buscar_vuelos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import androidx.compose.foundation.lazy.items
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
                viewModel.onQueryChange(it)
            }
        )

        // Comprobamos si ha escrito algo o no en la caja de texto
        if (uiState.searchQuery.isEmpty()) {
            val favoriteList = uiState.favoriteList
            val airportList = uiState.airportList

            if (favoriteList.isNotEmpty()){

            } else {
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
            }
        } else {
            val airports = uiState.airportList

            SearchResults(
                airports = airports,
                onSelectCode = onSelectCode
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

@Composable
fun AirportRow(
    airport : Airport,
    onSelectCode: (String) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.my_gray_yellow))
            .padding(10.dp)
            .clickable(
                onClick = {
                    if (airport.code != "") onSelectCode(airport.code)
                }
            )
    ) {
        Text(
            text = airport.code,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = airport.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    airports: List<Airport>,
    onSelectCode: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        items(
            items = airports
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                AirportRow(
                    airport = it,
                    onSelectCode = onSelectCode
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}

@Preview(showBackground = true)
@Composable
fun AirportRowPreview() {
    AirportRow(
        Airport(
            code = "FCO",
            name = "Leonardo da Vinci International Airport"
        )
    )
}