package dev.alexpace.cryptovisual.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.FavoriteCryptoEntity

@Dao
interface FavoriteCryptoDao {

    /**
     * Get all favorite cryptos from the database
     */
    @Query("SELECT * FROM favorite_cryptos")
    fun getAll(): List<FavoriteCryptoEntity>?

    /**
     * Get a specific favorite crypto by id from the database
     */
    @Query("SELECT * FROM favorite_cryptos WHERE id LIKE :id LIMIT 1")
    fun findById(id: String): FavoriteCryptoEntity?

    /**
     * Check if a crypto is in the favorite_cryptos database table or not.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cryptos WHERE id = :id LIMIT 1)")
    fun isCryptoFavorite(id: String): LiveData<Boolean>

    /**
     * Insert a specific favorite crypto into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteCryptoEntity: FavoriteCryptoEntity)

    /**
     * Delete a specific favorite crypto from the database
     */
    @Query("DELETE FROM favorite_cryptos WHERE id = :id")
    fun deleteById(id: String)

}