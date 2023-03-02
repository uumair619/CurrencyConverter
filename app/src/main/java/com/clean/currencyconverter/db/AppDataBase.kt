package com.clean.currencyconverter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clean.currencyconverter.model.CurrencyData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Database(entities = [(CurrencyData::class)], version = 1, exportSchema = false)
@TypeConverters(MapConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var sInstance: AppDatabase? = null
        private const val DATABASE_NAME: String = "RatesList"
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class) {
                    sInstance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return sInstance as AppDatabase
        }
    }

    abstract fun ratesDao(): CurrencyDao
}