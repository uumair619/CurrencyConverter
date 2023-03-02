package com.clean.currencyconverter.api

import com.clean.currencyconverter.model.CurrencyData
import retrofit2.http.GET

interface CurrencyApiService {

   @GET("latest/usd")
    suspend fun getCurrencyData() : CurrencyData


}