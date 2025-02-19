package dev.alexpace.cryptovisual.domain.models

data class CryptoHistory(
    val id: Int,
    val cryptoId: String,
    val timestamp: Long,
    val price: Double,
    val marketCap: Double,
    val totalVolume: Double
)