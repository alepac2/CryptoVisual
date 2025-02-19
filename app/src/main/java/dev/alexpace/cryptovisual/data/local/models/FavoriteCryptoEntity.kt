package dev.alexpace.cryptovisual.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cryptos")
data class FavoriteCryptoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "current_price") val currentPrice: Double,
    @ColumnInfo(name = "market_cap") val marketCap: Double,
    @ColumnInfo(name = "total_volume") val totalVolume: Double
)