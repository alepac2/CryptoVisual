package dev.alexpace.cryptovisual.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.alexpace.cryptovisual.data.local.models.CryptoEntity
import dev.alexpace.cryptovisual.data.local.dao.CryptoDao
import dev.alexpace.cryptovisual.data.local.dao.FavouriteCryptoDao
import dev.alexpace.cryptovisual.data.local.models.FavouriteCryptoEntity

@Database(entities = [CryptoEntity::class, FavouriteCryptoEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

    abstract fun favouriteCryptoDao(): FavouriteCryptoDao

    // To prepopulate the database with data, once the app has been executed at least once:
    // https://developer.android.com/training/data-storage/room/prepopulate
}