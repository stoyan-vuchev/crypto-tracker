package com.stoyanvuchev.cryptotracker.crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stoyanvuchev.cryptotracker.crypto.data.local.entity.CoinEntity

/**
 * Represents the local Room database for the application.
 *
 * This abstract class extends [RoomDatabase] and serves as the main access point for the underlying
 * SQLite database. It defines the database entities and version, as well as the data access object (DAO).
 *
 * @property dao The data access object that provides methods for accessing the database.
 *
 * @constructor Creates a local database instance with specified entities and version.
 */
@Database(
    entities = [CoinEntity::class],
    version = 1,
    autoMigrations = []
)
abstract class LocalDatabase : RoomDatabase() {

    /**
     * Gets the data access object for interacting with the database.
     *
     * This property provides access to methods defined in [LocalDatabaseDao] for performing
     * CRUD operations on the database.
     */
    abstract val dao: LocalDatabaseDao

}