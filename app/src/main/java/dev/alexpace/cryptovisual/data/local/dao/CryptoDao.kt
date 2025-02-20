package dev.alexpace.cryptovisual.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity

@Dao
interface CryptoDao {

    /**
     * Get all cryptos from the database
     */
    @Query("SELECT * FROM cryptos")
    fun getAll(): List<CryptoEntity>

    /**
     * Get a specific crypto by id from the database
     */
    @Query("SELECT * FROM cryptos WHERE id LIKE :id LIMIT 1")
    fun findById(id: String): CryptoEntity?

    /**
     * Insert all cryptos into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptoEntities: List<CryptoEntity>)

    /**
     * Insert a specific crypto into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptoEntity: CryptoEntity)

}
