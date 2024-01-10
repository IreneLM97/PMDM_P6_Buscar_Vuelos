package com.example.pmdm_p6_buscar_vuelos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pmdm_p6_buscar_vuelos.model.Airport
import com.example.pmdm_p6_buscar_vuelos.model.Favorite

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightDatabase : RoomDatabase() {

    abstract fun flightDao(): FlightDao

    companion object {
        @Volatile
        private var Instance: FlightDatabase? = null

        fun getDatabase(context: Context): FlightDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance
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