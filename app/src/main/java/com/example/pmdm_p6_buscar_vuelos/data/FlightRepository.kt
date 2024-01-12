package com.example.pmdm_p6_buscar_vuelos.data

import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getAllAirports(): Flow<List<Airport>>
    fun getAllAirports(query: String): Flow<List<Airport>>
    suspend fun getAirportByCode(code: String): Airport
    suspend fun getAllAirportsNoCode(code: String): List<Airport>

    fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite?
    suspend fun insertFavorite(flight: Favorite)
    suspend fun deleteFavorite(flight: Favorite)
}