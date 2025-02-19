package dev.alexpace.cryptovisual.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity

@Dao
interface CryptoDao {
    @Query("SELECT * FROM cryptos")
    fun getAll(): List<CryptoEntity>

    @Query(
        "SELECT * FROM cryptos WHERE id LIKE :id LIMIT 1"
    )
    fun findById(id: String): CryptoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptoEntities: List<CryptoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptoEntity: CryptoEntity)

}
