package dev.alexpace.cryptovisual.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_history")
data class CryptoHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "crypto_id")
    val cryptoId: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "market_cap")
    val marketCap: Double,
    @ColumnInfo(name = "total_volume")
    val totalVolume: Double
)
