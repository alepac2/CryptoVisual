package dev.alexpace.cryptovisual.data.remote.service

import dev.alexpace.cryptovisual.data.remote.models.CryptoHistoryResponse
import dev.alexpace.cryptovisual.data.remote.models.CryptoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * For all cryptos with a limit of 250
     */
    @GET("coins/markets")
    suspend fun getCryptos(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 250,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<CryptoResponse>

    /**
     * For a specific crypto by id
     */
    @GET("coins/{id}")
    suspend fun getCryptoById(
        @Path("id") id: String
    ): CryptoResponse

    /**
     * For a specific crypto's history
     */
    @GET("coins/{id}/market_chart")
    suspend fun getCryptoHistory(
        @Path("id") id: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: Int = 30
    ): CryptoHistoryResponse

    /**
     * For a specific crypto's history by date range
     */
    @GET("coins/{id}/market_chart/range")
    suspend fun getCryptoHistoryByDateRange(
        @Path("id") id: String,
        @Query("from") dateStart: Long,
        @Query("to") dateEnd: Long,
        @Query("vs_currency") currency: String = "usd"
    ): CryptoHistoryResponse
}
