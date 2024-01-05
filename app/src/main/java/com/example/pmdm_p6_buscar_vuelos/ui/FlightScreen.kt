package com.example.pmdm_p6_buscar_vuelos.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.inventory.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    // Diseño de la estructura básica de la pantalla
    Scaffold(
        topBar = {
            // Barra superior personalizada
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.flight_search))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.my_dark_green)
                )
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}