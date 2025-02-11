package dev.alexpace.cryptovisual.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.alexpace.cryptovisual.data.local.models.Crypto
import dev.alexpace.cryptovisual.data.local.dao.CryptoDao

@Database(entities = [Crypto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}