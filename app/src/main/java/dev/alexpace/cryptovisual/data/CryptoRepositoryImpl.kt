package dev.alexpace.cryptovisual.data

import android.util.Log
import androidx.lifecycle.LiveData
import dev.alexpace.cryptovisual.data.local.db.AppDatabase
import dev.alexpace.cryptovisual.data.remote.service.ApiService
import dev.alexpace.cryptovisual.data.remote.client.RetrofitClient
import dev.alexpace.cryptovisual.domain.CryptoRepository
import dev.alexpace.cryptovisual.domain.models.Crypto
import dev.alexpace.cryptovisual.domain.models.CryptoHistory
import retrofit2.HttpException

class CryptoRepositoryImpl(db: AppDatabase) : CryptoRepository {

    // Values
    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)
    private val cryptoDao = db.cryptoDao()
    private val favoriteCryptoDao = db.favoriteCryptoDao()
    private val cryptoHistoryDao = db.cryptoHistoryDao()

    /**
     * Fetch cryptos from db first, if not found it fetches them from the API,
     * insert them into the database and return them
     */
    override suspend fun getCryptos(): List<Crypto> {

        return try {
            val cryptosFromDb = cryptoDao.getAll()
            if (cryptosFromDb.isNotEmpty()) {
                return cryptosFromDb.map { it.toDomain() }
            }

            val dbEntities = apiService.getCryptos().map { it.toDatabase() }
            cryptoDao.insertAll(dbEntities)

            dbEntities.map { it.toDomain() }
        } catch (e: HttpException) {
            Log.e("MainDebug", "HTTP Error: ${e.code()} - ${e.message()}")
            emptyList()
        }
    }

    /**
     * Fetch crypto by id from the db first, if not found it fetches them from
     * the API, insert it into the database and return it
     */
    override suspend fun getCryptoById(id: String): Crypto? {

        val cryptoFromDb = cryptoDao.findById(id)
        if (cryptoFromDb != null) {
            return cryptoFromDb.toDomain()
        }

        val cryptoFromApi = try {
            apiService.getCryptoById(id)
        } catch (e: HttpException) {
            Log.e(
                "MainDebug",
                "HTTP error when fetching crypto: ${e.code()} - ${e.message()}"
            )
            return null
        }

        val dbCrypto = cryptoFromApi.toDatabase()
        cryptoDao.insert(dbCrypto)
        return dbCrypto.toDomain()
    }

    /**
     * Adds crypto to favorite_cryptos database table
     */
    override suspend fun addToFavorites(crypto: Crypto) {
        favoriteCryptoDao.insert(crypto.toDatabase())
        Log.d("MainDebug", "Added ${crypto.name} to favorites")
    }

    /**
     * Fetch favorite cryptos from the database
     */
    override suspend fun getFavoriteCryptos(): List<Crypto>? {
        val favouriteCryptoListFromDb = favoriteCryptoDao.getAll()?.map {
            it.toDomain()
        }

        if (favouriteCryptoListFromDb != null) {
            return favouriteCryptoListFromDb
        }

        return null
    }

    /**
     * Fetch favorite crypto by id from the database
     */
    override suspend fun getFavoriteCryptoById(id: String): Crypto? {
        val favouriteCryptoFromDb = favoriteCryptoDao.findById(id)

        if (favouriteCryptoFromDb != null) {
            return favouriteCryptoFromDb.toDomain()
        }

        return null
    }

    /**
     * Checks whether a crypto is in the favorite_cryptos database table or not.
     */
    override fun isCryptoFavorite(cryptoId: String): LiveData<Boolean> {
        return favoriteCryptoDao.isCryptoFavorite(cryptoId)
    }

    /**
     * Removes crypto from favorite_cryptos database table
     */
    override suspend fun removeFromFavorites(cryptoId: String) {
        favoriteCryptoDao.deleteById(cryptoId)
        Log.d("MainDebug", "Removed $cryptoId from favorites")
    }

    /**
     * Fetch crypto history and insert it into the database if not found
     */
    override suspend fun getCryptoHistory(cryptoId: String): List<CryptoHistory> {

        val historyFromDb = cryptoHistoryDao.getCryptoHistory(cryptoId)
        if (historyFromDb.isNotEmpty()) {
            return historyFromDb.map { it.toDomain() }
        }

        try {
            val historyFromApi = apiService.getCryptoHistory(cryptoId)
            val dbHistory = historyFromApi.toDatabase(cryptoId)

            cryptoHistoryDao.insertAll(dbHistory)

            return dbHistory.map { it.toDomain() }
        } catch (e: HttpException) {
            Log.e(
                "MainDebug",
                "HTTP error when fetching crypto history: ${e.code()} - ${e.message()}"
            )
            return emptyList()
        }
    }
}
