package dev.alexpace.cryptovisual.data

import dev.alexpace.cryptovisual.data.remote.models.Crypto
import dev.alexpace.cryptovisual.data.remote.service.ApiService
import dev.alexpace.cryptovisual.data.remote.client.RetrofitClient

class CryptoRepository {

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    suspend fun getCryptos(): List<Crypto> {
        return apiService.getCryptos()
    }
}
