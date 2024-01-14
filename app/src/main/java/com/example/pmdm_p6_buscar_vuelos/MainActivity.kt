package com.example.pmdm_p6_buscar_vuelos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pmdm_p6_buscar_vuelos.ui.theme.PMDM_P6_Buscar_VuelosTheme

/**
 * Actividad principal que define la estructura inicial de la aplicación.
 */
class MainActivity : ComponentActivity() {
    /**
     * Método que se ejecuta al crear la actividad.
     *
     * @param savedInstanceState Objeto Bundle que contiene el estado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMDM_P6_Buscar_VuelosTheme {
                // Contenedor de la aplicación
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Iniciamos la aplicación de vuelos
                    FlightApp()
                }
            }
        }
    }
}