package dev.alexpace.cryptovisual.data

import dev.alexpace.cryptovisual.data.models.Crypto
import dev.alexpace.cryptovisual.data.remote.ApiService
import dev.alexpace.cryptovisual.data.remote.RetrofitClient

class CryptoRepository {

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    suspend fun getCryptos(): List<Crypto> {
        return apiService.getCryptos()
    }
}
