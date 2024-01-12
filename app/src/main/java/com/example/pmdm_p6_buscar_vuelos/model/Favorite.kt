package com.example.pmdm_p6_buscar_vuelos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Clase de datos que representa un favorito y se corresponde con la entidad Favorite de la base de datos Room.
 *
 * @param id Identificador único del favorito (clave primaria que se genera automáticamente).
 * @param departureCode Código IATA del aeropuerto de salida.
 * @param destinationCode Código IATA del aeropuerto de llegada.
 */
@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)
