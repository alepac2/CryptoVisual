package dev.alexpace.cryptovisual.data.remote.models

import com.squareup.moshi.Json

data class CryptoHistoryResponse(
    @Json(name = "prices") val prices: List<List<Double>>,
    @Json(name = "market_caps") val marketCaps: List<List<Double>>,
    @Json(name = "total_volumes") val totalVolumes: List<List<Double>>
)
