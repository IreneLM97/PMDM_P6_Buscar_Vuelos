package com.example.pmdm_p6_buscar_vuelos.di

import android.content.Context
import com.example.pmdm_p6_buscar_vuelos.data.FlightDatabase
import com.example.pmdm_p6_buscar_vuelos.data.FlightRepository
import com.example.pmdm_p6_buscar_vuelos.data.OfflineFlightRepository

/**
 * Interfaz que define las dependencias de la aplicación.
 */
interface AppContainer {
    val flightRepository: FlightRepository
}

/**
 * Implementación de [AppContainer] que proporciona instancias concretas de las dependencias de la aplicación.
 *
 * @property context Contexto de la aplicación.
 */
class AppDataContainer (
    private val context: Context
) : AppContainer {

    /**
     * [FlightRepository] que utiliza un [OfflineFlightRepository] construido a partir de la base de datos Room.
     */
    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }
}