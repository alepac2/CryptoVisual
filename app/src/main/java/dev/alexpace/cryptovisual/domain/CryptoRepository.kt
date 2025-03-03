package dev.alexpace.cryptovisual.domain

import androidx.lifecycle.LiveData
import dev.alexpace.cryptovisual.domain.models.Crypto
import dev.alexpace.cryptovisual.domain.models.CryptoHistory

interface CryptoRepository {

    // Cryptos
    suspend fun getCryptos(): List<Crypto>
    suspend fun getCryptoById(id: String): Crypto?

    // Favourite cryptos
    suspend fun addToFavorites(crypto: Crypto)
    suspend fun getFavoriteCryptos(): List<Crypto>?
    suspend fun getFavoriteCryptoById(id: String): Crypto?
    fun isCryptoFavorite(cryptoId: String): LiveData<Boolean>
    suspend fun removeFromFavorites(cryptoId: String)

    // Crypto history
    suspend fun getCryptoHistory(cryptoId: String): List<CryptoHistory>
    suspend fun getCryptoHistoryByDateRange(
        cryptoId: String, dateStart: Long, dateEnd: Long
    ): List<CryptoHistory>

}