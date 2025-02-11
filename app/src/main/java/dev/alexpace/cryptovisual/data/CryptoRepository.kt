package dev.alexpace.cryptovisual.data

import android.util.Log
import dev.alexpace.cryptovisual.data.local.db.AppDatabase
import dev.alexpace.cryptovisual.data.remote.service.ApiService
import dev.alexpace.cryptovisual.data.remote.client.RetrofitClient
import dev.alexpace.cryptovisual.domain.models.Crypto
import retrofit2.HttpException

class CryptoRepository(db: AppDatabase) {

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val cryptoDao = db.cryptoDao()

    suspend fun getCryptos(): List<Crypto> {

        try {
            val dbEntities = apiService.getCryptos().map {
                it.toDatabase()
            }

            cryptoDao.insertAll(dbEntities)
            Log.d("MainDebug", "Inserted ${dbEntities.size} cryptos into the database")
        } catch (e: HttpException) {
            Log.e("MainDebug", "Error HTTP: ${e.code()} - ${e.message()}")
        }

        return cryptoDao.getAll().map {
            it.toDomain()
        }
    }

}
