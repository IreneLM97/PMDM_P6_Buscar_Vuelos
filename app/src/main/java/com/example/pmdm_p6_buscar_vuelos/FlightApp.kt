package com.example.pmdm_p6_buscar_vuelos

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pmdm_p6_buscar_vuelos.ui.AppViewModelProvider
import com.example.pmdm_p6_buscar_vuelos.ui.search.SearchScreen
import com.example.pmdm_p6_buscar_vuelos.ui.search.SearchViewModel

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
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current

        val viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val searchUiState = viewModel.searchUiState.collectAsState().value
        val favoriteUiState by viewModel.favoriteUiState.collectAsState()

        SearchScreen(
            modifier = Modifier.padding(it),
            onQueryChanged = { query ->
                viewModel.updateSelectedCode("")
                viewModel.onQueryChanged(query)
                if(query.isEmpty()) focusManager.clearFocus()
            },
            onCodeClicked = { code ->
                viewModel.updateSelectedCode(code)
                viewModel.onCodeClicked(code)
                focusManager.clearFocus()
            },
            onFavoriteClicked = { depCode, destCode ->
                viewModel.onFavoriteClicked(depCode, destCode)
            },
            onSendButtonClicked = { summary: String ->
                shareFlight(context, summary = summary)  // compartimos la información
            }
        )
    }
}


/**
 * Función que permite compartir la información de un lugar a otra aplicación.
 *
 * @param context contexto de la aplicación
 * @param summary resumen del vuelo que se quiere compartir
 */
private fun shareFlight(
    context: Context,
    summary: String
) {
    // Crear un Intent de acción SEND para compartir
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"  // contenido de texto plano
        putExtra(Intent.EXTRA_TEXT, summary)  // agregamos resumen
    }

    // Iniciar una actividad para elegir la aplicación de destino a la que se quiere compartir
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.send_flight)
        )
    )
}