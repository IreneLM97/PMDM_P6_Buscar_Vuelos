package com.example.pmdm_p6_buscar_vuelos.ui.screens

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

@Composable
fun AirportRow(
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    airport : Airport,
    onSelectCode: (String) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(enabled = isClickable) {
                if (airport.code != "") onSelectCode(airport.code)
            }
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
fun AirportResults(
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
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                AirportRow(
                    modifier = Modifier
                        .background(colorResource(id = R.color.my_gray_green)),
                    airport = it,
                    onSelectCode = onSelectCode
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirportResultsPreview() {
    AirportResults(
        airports = listOf(
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