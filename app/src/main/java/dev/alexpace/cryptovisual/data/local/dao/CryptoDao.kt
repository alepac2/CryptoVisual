package dev.alexpace.cryptovisual.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.Crypto

@Dao
interface CryptoDao {
    @Query("SELECT * FROM crypto")
    fun getAll(): List<Crypto>

    @Query("SELECT * FROM crypto WHERE id IN (:cryptoIds)")
    fun loadAllByIds(cryptoIds: Array<String>): List<Crypto>

    @Query(
        "SELECT * FROM crypto WHERE name LIKE :name LIMIT 1"
    )
    fun findByName(name: String): Crypto

    @Insert
    fun insertAll(vararg users: Crypto)

    @Delete
    fun delete(user: Crypto)
}
