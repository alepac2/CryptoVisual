package dev.alexpace.cryptovisual.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity
import dev.alexpace.cryptovisual.data.local.dao.CryptoDao

@Database(entities = [CryptoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao

    // To prepopulate the database with data, once the app has been executed at least once:
    // https://developer.android.com/training/data-storage/room/prepopulate
}