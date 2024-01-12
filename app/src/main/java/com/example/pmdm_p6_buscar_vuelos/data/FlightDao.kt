package com.example.pmdm_p6_buscar_vuelos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Query("SELECT * FROM airport ORDER BY id ASC")
    suspend fun getAllAirports(): List<Airport>

    @Query("SELECT * FROM airport WHERE iata_code = :query OR name LIKE '%' || :query || '%' ORDER BY passengers DESC")
    fun getAllAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :code")
    suspend fun getAirportByCode(code: String): Airport

    @Query("SELECT * FROM airport WHERE iata_code != :code ORDER BY id ASC")
    suspend fun getAllAirportsNoCode(code: String): List<Airport>

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(flight: Favorite)
    @Delete
    suspend fun deleteFavorite(flight: Favorite)
}