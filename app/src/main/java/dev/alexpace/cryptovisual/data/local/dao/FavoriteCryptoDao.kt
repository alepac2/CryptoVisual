package dev.alexpace.cryptovisual.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.FavoriteCryptoEntity

@Dao
interface FavoriteCryptoDao {

    @Query("SELECT * FROM favorite_cryptos")
    fun getAll(): List<FavoriteCryptoEntity>?

    @Query("SELECT * FROM favorite_cryptos WHERE id LIKE :id LIMIT 1")
    fun findById(id: String): FavoriteCryptoEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cryptos WHERE id = :id LIMIT 1)")
    fun isCryptoFavorite(id: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteCryptoEntity: FavoriteCryptoEntity)

    @Query("DELETE FROM favorite_cryptos WHERE id = :id")
    fun deleteById(id: String)

}