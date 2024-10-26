package com.stoyanvuchev.cryptotracker.crypto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stoyanvuchev.cryptotracker.crypto.data.local.entity.CoinEntity

/**
 * Data access object (DAO) for performing CRUD database operations on the `coin_table`.
 *
 * This interface defines methods for inserting, retrieving, and deleting [CoinEntity] entities in the local database.
 */
@Dao
interface LocalDatabaseDao {

    /**
     * Inserts a list of [CoinEntity] entities into the database.
     *
     * If any of the entities already exist, they will be replaced.
     *
     * @param coins The list of [CoinEntity] entities to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    /**
     * Retrieves all [CoinEntity] records from the `coin_table`.
     *
     * @return A list of [CoinEntity] entities, or `null` if the table is empty.
     */
    @Query("SELECT * FROM coin_table")
    suspend fun getAllCoins(): List<CoinEntity>?

    /**
     * Retrieves a single [CoinEntity] by its symbol.
     *
     * @param symbol The symbol of the coin to retrieve.
     * @return A [CoinEntity] entity matching the given symbol, or `null` if no match is found.
     */
    @Query("SELECT * FROM coin_table WHERE symbol = :symbol LIMIT 1")
    suspend fun getCoinBySymbol(symbol: String): CoinEntity?

    /**
     * Deletes all entity records from the `coin_table`.
     *
     * This method clears all [CoinEntity] entries in the database.
     */
    @Query("DELETE FROM coin_table")
    suspend fun deleteAllCoins()

}