package com.example.pmdm_p6_buscar_vuelos.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

/**
 * Clase abstracta que representa la base de datos Room para la aplicación.
 */
@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightDatabase : RoomDatabase() {

    /**
     * Método abstracto para obtener el DAO (Data Access Object) de la base de datos.
     *
     * @return Instancia del DAO para interactuar con la base de datos.
     */
    abstract fun flightDao(): FlightDao

    /**
     * Companion object que proporciona métodos para obtener la instancia de la base de datos.
     */
    companion object {
        @Volatile
        private var Instance: FlightDatabase? = null

        /**
         * Método para obtener la instancia de la base de datos.
         *
         * @param context Contexto de la aplicación.
         * @return Instancia de la base de datos.
         */
        fun getDatabase(context: Context): FlightDatabase {
            // Si la instancia no es nula, devuélvela;
            // de lo contrario, crea una nueva instancia de la base de datos.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FlightDatabase::class.java,
                    "flight_database"
                )
                    .createFromAsset("database/flight_search.db")
                    /** Con esta línea, Room eliminará los cambios producidos durante la ejecución
                     * y cogerá la base de datos como estaba inicialmente, esto afecta a que no
                     * aparecerán los favoritos al slide y volver a entrar en la aplicación.
                     */
                    // .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}