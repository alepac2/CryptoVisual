package dev.alexpace.cryptovisual.data

import dev.alexpace.cryptovisual.data.local.models.CryptoEntity
import dev.alexpace.cryptovisual.data.remote.models.CryptoResponse
import dev.alexpace.cryptovisual.domain.models.Crypto

fun CryptoResponse.toDatabase() = CryptoEntity(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice,
    this.marketCap,
    this.totalVolume
)

fun CryptoEntity.toDomain() = Crypto(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice,
    this.marketCap,
    this.totalVolume
)