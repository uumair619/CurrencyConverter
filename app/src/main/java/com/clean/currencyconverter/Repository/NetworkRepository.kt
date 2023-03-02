package com.clean.currencyconverter.Repository

import com.clean.currencyconverter.api.CurrencyApiService
import com.clean.currencyconverter.api.NetworkResult
import com.clean.currencyconverter.db.AppDatabase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val currencyApiService: CurrencyApiService ,
                                            private val database: AppDatabase) {


    suspend fun getCurrencyData() = flow {
        emit(NetworkResult.Loading(true))
        val response =currencyApiService.getCurrencyData()
        database.ratesDao().insertRates(response)
        emit(NetworkResult.Success(response))
    }.catch {e ->
        //load from DB
        var databaseData = database.ratesDao().loadRates()
        databaseData?.let {
            emit(NetworkResult.Success(databaseData))
        } ?: run {
            emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        }


    }
}