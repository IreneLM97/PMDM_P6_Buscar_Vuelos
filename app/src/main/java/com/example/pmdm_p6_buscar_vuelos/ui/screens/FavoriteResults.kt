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
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

@Composable
fun FavoriteRow(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = true,
    departureCode: String,
    destinationCode: String,
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
                    Text(
                        text = departureCode
                    )
                    Text(
                        text = stringResource(R.string.arrive_label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    Text(
                        text = destinationCode
                    )
                }
            }
            IconButton(
                onClick = {
                    onFavoriteClick(departureCode, destinationCode)
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
fun FavoriteResults(
    modifier: Modifier = Modifier,
    favorites: List<Favorite>,
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
            text = stringResource(R.string.favorites_flights)
        )
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(
                items = favorites
            ) { item ->
                FavoriteRow(
                    departureCode = item.departureCode,
                    destinationCode = item.destinationCode,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}