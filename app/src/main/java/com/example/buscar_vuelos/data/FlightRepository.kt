package com.example.buscar_vuelos.data

import com.example.buscar_vuelos.model.Airport
import com.example.buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getAllAirports(query: String): Flow<List<Airport>>
    fun getAirportById(id: Int): Flow<Airport>

    fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite
    suspend fun insertFavorite(flight: Favorite)
    suspend fun deleteFavorite(flight: Favorite)
}