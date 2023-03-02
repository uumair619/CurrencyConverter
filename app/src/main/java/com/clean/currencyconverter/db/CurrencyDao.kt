package com.clean.currencyconverter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clean.currencyconverter.model.CurrencyData

@Dao
abstract class CurrencyDao {
    @Query("SELECT * FROM currency")
    abstract fun loadRates(): CurrencyData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRates(rates: CurrencyData)
}