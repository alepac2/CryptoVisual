package dev.alexpace.cryptovisual.data

import android.util.Log
import dev.alexpace.cryptovisual.data.local.db.AppDatabase
import dev.alexpace.cryptovisual.data.remote.service.ApiService
import dev.alexpace.cryptovisual.data.remote.client.RetrofitClient
import dev.alexpace.cryptovisual.domain.CryptoRepository
import dev.alexpace.cryptovisual.domain.models.Crypto
import retrofit2.HttpException

class CryptoRepositoryImpl(db: AppDatabase): CryptoRepository {

    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val cryptoDao = db.cryptoDao()

    override suspend fun getCryptos(): List<Crypto> {

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

    override suspend fun getCryptoById(id: String): Crypto? {

        // Try to retrieve it from db
        val cryptoFromDb = cryptoDao.findById(id)
        if (cryptoFromDb != null) {
            return cryptoFromDb.toDomain()
        }

        // If not found, try to retrieve it from Api
        val cryptoFromApi = try {
            apiService.getCryptoById(id)
        } catch (e: HttpException) {
            Log.e("MainDebug", "HTTP error when fetching crypto: ${e.code()} - ${e.message()}")
            return null
        }

        if (cryptoFromApi != null) {
            val dbCrypto = cryptoFromApi.toDatabase()
            cryptoDao.insert(dbCrypto)
            return dbCrypto.toDomain()
        }

        return null
    }

}
