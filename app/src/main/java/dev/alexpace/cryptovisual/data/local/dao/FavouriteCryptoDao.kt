package dev.alexpace.cryptovisual.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.FavouriteCryptoEntity

@Dao
interface FavouriteCryptoDao {

    @Query("SELECT * FROM favourite_cryptos")
    fun getAll(): List<FavouriteCryptoEntity>?

    @Query("SELECT * FROM favourite_cryptos WHERE id LIKE :id LIMIT 1")
    fun findById(id: String): FavouriteCryptoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favouriteCryptoEntity: FavouriteCryptoEntity)

}