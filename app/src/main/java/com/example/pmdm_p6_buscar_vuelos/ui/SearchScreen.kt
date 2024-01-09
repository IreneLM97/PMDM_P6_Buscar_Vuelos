package com.example.pmdm_p6_buscar_vuelos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R
import com.example.pmdm_p6_buscar_vuelos.model.Airport

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    // Estado para el texto de búsqueda
    var query by rememberSaveable { mutableStateOf("") }

    // Columna principal que contiene los elementos de la pantalla
    Column(
        modifier = modifier
    ) {
        // Campo de texto para ingresar la consulta de búsqueda
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it },
            singleLine = true,
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
                    color = colorResource(id = R.color.my_dark_yellow),
                    shape = RoundedCornerShape(40.dp)
                )
        )
    }
}

@Composable
fun AirportRow(
    airport : Airport
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Text(
            fontWeight = FontWeight.Bold,
            text = airport.code
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 45.dp),
            text = airport.name
        )
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