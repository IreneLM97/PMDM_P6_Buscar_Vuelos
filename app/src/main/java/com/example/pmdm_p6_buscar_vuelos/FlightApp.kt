package com.example.pmdm_p6_buscar_vuelos

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.pmdm_p6_buscar_vuelos.ui.screens.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightApp() {
    Scaffold(
        topBar = {
            // Barra superior personalizada
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.flight_search),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.my_green)
                )
            )
        }
    )
    {
        innerPadding ->
        SearchScreen(
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}