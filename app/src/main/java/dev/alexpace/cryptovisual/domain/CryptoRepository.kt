package dev.alexpace.cryptovisual.domain

import dev.alexpace.cryptovisual.domain.models.Crypto

interface CryptoRepository {
    suspend fun getCryptos(): List<Crypto>
}