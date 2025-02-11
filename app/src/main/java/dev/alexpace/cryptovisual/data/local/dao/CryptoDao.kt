package dev.alexpace.cryptovisual.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity

@Dao
interface CryptoDao {
    @Query("SELECT * FROM cryptos")
    fun getAll(): List<CryptoEntity>

    @Query("SELECT * FROM cryptos WHERE id IN (:cryptoIds)")
    fun loadAllByIds(cryptoIds: Array<String>): List<CryptoEntity>

    @Query(
        "SELECT * FROM cryptos WHERE name LIKE :name LIMIT 1"
    )
    fun findByName(name: String): CryptoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptoEntities: List<CryptoEntity>)

    @Delete
    fun delete(user: CryptoEntity)
}
