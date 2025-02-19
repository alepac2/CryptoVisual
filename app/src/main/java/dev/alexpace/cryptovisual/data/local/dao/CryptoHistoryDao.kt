package dev.alexpace.cryptovisual.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.alexpace.cryptovisual.data.local.models.CryptoHistoryEntity

@Dao
interface CryptoHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CryptoHistoryEntity>)

    @Query("SELECT * FROM crypto_history WHERE crypto_id = :cryptoId ORDER BY timestamp ASC")
    suspend fun getCryptoHistory(cryptoId: String): List<CryptoHistoryEntity>

    // For later
    @Query("SELECT * FROM crypto_history WHERE crypto_id = :cryptoId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp ASC")
    suspend fun getCryptoHistoryByDateRange(cryptoId: String, startDate: Long, endDate: Long): List<CryptoHistoryEntity>

}