package com.example.pmdm_p6_buscar_vuelos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Clase de datos que representa un aeropuerto y se corresponde con la entidad Airport de la base de datos Room.
 *
 * @param id Identificador único del aeropuerto (clave primaria).
 * @param code Código IATA del aeropuerto.
 * @param name Nombre del aeropuerto.
 * @param passengers Número de pasajeros del aeropuerto.
 */
@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo("iata_code")
    val code: String = "",
    val name: String = "",
    val passengers: Int = 0
)
