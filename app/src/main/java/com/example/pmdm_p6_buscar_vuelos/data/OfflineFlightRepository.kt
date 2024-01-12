package com.example.pmdm_p6_buscar_vuelos.data

import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow

/**
 * Implementación de [FlightRepository] que utiliza un [FlightDao] para operaciones offline.
 *
 * @property flightDao DAO para acceder a la base de datos Room.
 */
class OfflineFlightRepository(
    private val flightDao: FlightDao
) : FlightRepository {
    override fun getAllAirports(): Flow<List<Airport>> {
        return flightDao.getAllAirports()
    }

    override fun getAllAirports(query: String): Flow<List<Airport>> {
        return flightDao.getAllAirports(query)
    }

    override suspend fun getAirportByCode(code: String): Airport {
        return flightDao.getAirportByCode(code)
    }

    override suspend fun getAllAirportsNoCode(code: String): List<Airport> {
        return flightDao.getAllAirportsNoCode(code)
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return flightDao.getAllFavorites()
    }

    override suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite? {
        return flightDao.getFavoriteByInfo(departureCode, destinationCode)
    }
    override suspend fun insertFavorite(favorite: Favorite) {
        return flightDao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        return flightDao.deleteFavorite(favorite)
    }
}