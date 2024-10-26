package com.stoyanvuchev.cryptotracker.crypto.data.local

import android.content.Context
import androidx.room.Room

/**
 * Factory object for creating instances of [LocalDatabase].
 *
 * This object provides methods to create both persistent and in-memory instances of the database.
 */
object LocalDatabaseFactory {

    /**
     * Creates a persistent instance of [LocalDatabase].
     *
     * This method uses the provided [context] to build a Room database with a specified name ("local_db").
     * The database will persist across application restarts.
     *
     * @param context The application [Context] used to create the database instance.
     * @return A configured persistent instance of [LocalDatabase].
     */
    fun createPersistentInstance(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = LocalDatabase::class.java,
            name = "local_db"
        ).build()
    }

    /**
     * Creates an in-memory instance of [LocalDatabase].
     *
     * This method uses the provided [context] to build a Room database that exists only in memory and
     * is not persisted to disk. This is useful for testing or temporary data storage.
     *
     * @param context The application [Context] used to create the in-memory database instance.
     * @return A configured in-memory instance of [LocalDatabase].
     */
    fun createInMemoryInstance(context: Context): LocalDatabase {
        return Room.inMemoryDatabaseBuilder(
            context = context,
            klass = LocalDatabase::class.java
        ).build()
    }

}