package com.example.pmdm_p6_buscar_vuelos.data

import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(
    private val flightDao: FlightDao
) : FlightRepository {
    override fun getAllAirports(query: String): Flow<List<Airport>> {
        return flightDao.getAllAirports(query)
    }

    override fun getAirportById(id: Int): Flow<Airport> {
        return flightDao.getAirportById(id)
    }

    override suspend fun getAllFavorites(): List<Favorite> {
        return flightDao.getAllFavorites()
    }

    override suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite {
        return flightDao.getFavoriteByInfo(departureCode, destinationCode)
    }
    override suspend fun insertFavorite(flight: Favorite) {
        return flightDao.insertFavorite(flight)
    }

    override suspend fun deleteFavorite(flight: Favorite) {
        return flightDao.deleteFavorite(flight)
    }
}