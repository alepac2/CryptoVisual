package dev.alexpace.cryptovisual.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity
import dev.alexpace.cryptovisual.data.local.dao.CryptoDao
import dev.alexpace.cryptovisual.data.local.dao.FavoriteCryptoDao
import dev.alexpace.cryptovisual.data.local.models.FavoriteCryptoEntity

@Database(entities = [CryptoEntity::class, FavoriteCryptoEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

    abstract fun favoriteCryptoDao(): FavoriteCryptoDao

    // To prepopulate the database with data, once the app has been executed at least once:
    // https://developer.android.com/training/data-storage/room/prepopulate
}