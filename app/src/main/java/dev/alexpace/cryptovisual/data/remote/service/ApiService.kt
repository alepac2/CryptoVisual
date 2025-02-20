package dev.alexpace.cryptovisual.data.remote.service

import dev.alexpace.cryptovisual.data.remote.models.CryptoHistoryResponse
import dev.alexpace.cryptovisual.data.remote.models.CryptoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // For all cryptos (Max. 250)
    @GET("coins/markets")
    suspend fun getCryptos(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 250,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<CryptoResponse>

    // For cryptos by ID
    @GET("coins/{id}")
    suspend fun getCryptoById(
        @Path("id") id: String
    ): CryptoResponse

    // For crypto history
    @GET("coins/{id}/market_chart")
    suspend fun getCryptoHistory(
        @Path("id") id: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: Int = 30
    ): CryptoHistoryResponse

}
