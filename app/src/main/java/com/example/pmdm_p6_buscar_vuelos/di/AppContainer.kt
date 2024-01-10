package com.example.pmdm_p6_buscar_vuelos.di

import android.content.Context
import com.example.pmdm_p6_buscar_vuelos.data.FlightDatabase
import com.example.pmdm_p6_buscar_vuelos.data.FlightRepository
import com.example.pmdm_p6_buscar_vuelos.data.OfflineFlightRepository


interface AppContainer {
    val flightRepository: FlightRepository
}

class AppDataContainer (
    private val context: Context
) : AppContainer {

    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }
}