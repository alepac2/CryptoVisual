package dev.alexpace.cryptovisual

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import dev.alexpace.cryptovisual.data.CryptoRepositoryImpl
import dev.alexpace.cryptovisual.data.local.db.AppDatabase
import dev.alexpace.cryptovisual.databinding.ActivityMainBinding
import dev.alexpace.cryptovisual.domain.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

//    //! TEST & TEMPORARY
//    private lateinit var db: AppDatabase
//    private lateinit var cryptoRepository: CryptoRepository
//    private val dbName = "cryptos.db"
//
//    lateinit var binding: ActivityMainBinding
//
//    private fun initRepository() {
//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, dbName
//        ).build()
//
//        cryptoRepository = CryptoRepositoryImpl(db)
//    }
//
//    //! TEST & TEMPORARY
//    private fun testApiCall() {
//
//        initRepository()
//
//        lifecycleScope.launch {
//            try {
//                val cryptos = withContext(Dispatchers.IO) {
//                    cryptoRepository.getCryptos()
//                }
//                cryptos.forEach {
//                    Log.d("MainDebug", "Crypto: ID: ${it.id}, Symbol: ${it.symbol}, Name: ${it.name}, Price: ${it.currentPrice}, Image: ${it.image}")
//                }
//            } catch (e: Exception) {
//                Log.e("MainDebug", "Error fetching data: ${e.message}")
//            }
//        }
//    }
}