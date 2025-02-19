package dev.alexpace.cryptovisual.domain

import dev.alexpace.cryptovisual.domain.models.Crypto

interface CryptoRepository {

    // Cryptos
    suspend fun getCryptos(): List<Crypto>
    suspend fun getCryptoById(id: String): Crypto?

    // Favourite cryptos
    suspend fun getFavouriteCryptos(): List<Crypto>?
    suspend fun getFavouriteCryptoById(id: String): Crypto?

}