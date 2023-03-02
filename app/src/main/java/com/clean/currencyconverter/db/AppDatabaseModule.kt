package com.clean.currencyconverter.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "RatesList")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideCurrencyDAO(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.ratesDao()
    }
}