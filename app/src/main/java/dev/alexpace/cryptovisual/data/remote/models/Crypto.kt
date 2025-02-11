package dev.alexpace.cryptovisual.data.remote.models

import com.squareup.moshi.Json

data class Crypto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @Json(name = "current_price") val currentPrice: Double,
    @Json(name = "market_cap") val marketCap: Double,
    @Json(name = "total_volume") val totalVolume: Double
)
