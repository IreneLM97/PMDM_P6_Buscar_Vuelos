package com.example.pmdm_p6_buscar_vuelos.data.room

import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz que define las operaciones disponibles para interactuar con la base de datos Room.
 */
interface FlightRepository {
    /**
     * Obtiene todos los aeropuertos ordenados por id.
     *
     * @return Flujo de lista de objetos Airport.
     */
    fun getAllAirports(): Flow<List<Airport>>

    /**
     * Obtiene todos los aeropuertos cuyo código IATA o nombre coincide con el valor de búsqueda dado.
     *
     * @param query Valor de búsqueda.
     * @return Flujo de lista de objetos Airport.
     */
    fun getAllAirports(query: String): Flow<List<Airport>>

    /**
     * Obtiene todos los favoritos ordenados por id.
     *
     * @return Flujo de lista de objetos Favorite.
     */
    fun getAllFavorites(): Flow<List<Favorite>>

    /**
     * Obtiene un favorito dado el código de los aeropuertos de salida y llegada.
     *
     * @param departureCode Código IATA del aeropuerto de salida.
     * @param destinationCode Código IATA del aeropuerto de llegada.
     * @return Objeto Favorite.
     */
    suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite?

    /**
     * Inserta un favorito en la base de datos, ignorando cualquier conflicto.
     *
     * @param favorite Objeto Favorite a insertar en la base de datos.
     */
    suspend fun insertFavorite(favorite: Favorite)

    /**
     * Elimina un favorito de la base de datos.
     *
     * @param favorite Objeto Favorite a eliminar de la base de datos.
     */
    suspend fun deleteFavorite(favorite: Favorite)
}