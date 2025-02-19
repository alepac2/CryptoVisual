package dev.alexpace.cryptovisual.data

import android.annotation.SuppressLint
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity
import dev.alexpace.cryptovisual.data.local.models.FavouriteCryptoEntity
import dev.alexpace.cryptovisual.data.remote.models.CryptoResponse
import dev.alexpace.cryptovisual.domain.models.Crypto

// Limit to two decimals
@SuppressLint("DefaultLocale")
fun Double.formatDecimals(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

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
    this.currentPrice.formatDecimals(),
    this.marketCap.formatDecimals(),
    this.totalVolume.formatDecimals()
)

fun FavouriteCryptoEntity.toDomain() = Crypto(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice.formatDecimals(),
    this.marketCap.formatDecimals(),
    this.totalVolume.formatDecimals()
)
