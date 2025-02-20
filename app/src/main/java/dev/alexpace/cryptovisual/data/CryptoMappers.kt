package dev.alexpace.cryptovisual.data

import android.annotation.SuppressLint
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity
import dev.alexpace.cryptovisual.data.local.models.CryptoHistoryEntity
import dev.alexpace.cryptovisual.data.local.models.FavoriteCryptoEntity
import dev.alexpace.cryptovisual.data.remote.models.CryptoHistoryResponse
import dev.alexpace.cryptovisual.data.remote.models.CryptoResponse
import dev.alexpace.cryptovisual.domain.models.Crypto
import dev.alexpace.cryptovisual.domain.models.CryptoHistory

/**
 * Formats the decimals of a double by rounding it to 2 decimals
 */
@SuppressLint("DefaultLocale")
fun Double.formatDecimals(): Double = String
    .format("%.2f", this)
    .replace(",", ".")
    .toDouble()

/**
 * Maps the CryptoResponse to CryptoEntity
 */
fun CryptoResponse.toDatabase() = CryptoEntity(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice,
    this.marketCap,
    this.totalVolume
)

/**
 * Maps the CryptoEntity to Crypto
 */
fun CryptoEntity.toDomain() = Crypto(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice.formatDecimals(),
    this.marketCap.formatDecimals(),
    this.totalVolume.formatDecimals()
)

/**
 * Maps the FavoriteCryptoEntity to Crypto
 */
fun FavoriteCryptoEntity.toDomain() = Crypto(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice.formatDecimals(),
    this.marketCap.formatDecimals(),
    this.totalVolume.formatDecimals()
)

/**
 * Maps the Crypto to FavoriteCryptoEntity
 */
fun Crypto.toDatabase() = FavoriteCryptoEntity(
    this.id,
    this.symbol,
    this.name,
    this.image,
    this.currentPrice,
    this.marketCap,
    this.totalVolume
)

/**
 * Maps the CryptoHistoryEntity to CryptoHistory
 */
fun CryptoHistoryEntity.toDomain() = CryptoHistory(
    this.id,
    this.cryptoId,
    this.timestamp,
    this.price.formatDecimals(),
    this.marketCap.formatDecimals(),
    this.totalVolume.formatDecimals()
)

/**
 * Maps the CryptoHistoryResponse to CryptoHistoryEntity
 */
fun CryptoHistoryResponse.toDatabase(cryptoId: String): List<CryptoHistoryEntity> {
    return this.prices.mapIndexed { index, priceEntry ->
        CryptoHistoryEntity(
            cryptoId = cryptoId,
            timestamp = (priceEntry[0] * 1000).toLong(),
            price = priceEntry[1],
            marketCap = this.marketCaps[index][1],
            totalVolume = this.totalVolumes[index][1]
        )
    }
}
