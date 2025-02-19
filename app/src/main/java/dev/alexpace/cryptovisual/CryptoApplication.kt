package dev.alexpace.cryptovisual

import android.app.Application
import android.content.Context
import androidx.room.Room
import dev.alexpace.cryptovisual.data.CryptoRepositoryImpl
import dev.alexpace.cryptovisual.data.local.db.AppDatabase
import dev.alexpace.cryptovisual.domain.CryptoRepository

class CryptoApplication : Application() {

    lateinit var cryptoRepository: CryptoRepository
    val dbName = "cryptos.db"


    private fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, dbName
        )
            .fallbackToDestructiveMigration() // For db schema changes
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val db = getDatabase(applicationContext)

        cryptoRepository = CryptoRepositoryImpl(db)
    }
}