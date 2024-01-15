package com.example.pmdm_p6_buscar_vuelos.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Función que muestra un icono de cargando página en movimiento.
 */
@Composable
fun LoadingIcon() {
    // Contenedor del icono de cargando
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Icono de cargando
        CircularProgressIndicator()
    }
}