package dev.alexpace.cryptovisual.domain.models

data class Crypto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Double,
    val totalVolume: Double
)
