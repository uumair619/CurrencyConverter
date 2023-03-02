package com.clean.currencyconverter.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency")
data class CurrencyData(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @field:SerializedName("result") val result: String,
    @field:SerializedName("provider") val provider: String,
    @field:SerializedName("documentation")val documentation: String,
    @field:SerializedName("terms_of_use")val termsOfUse: String,
    @field:SerializedName("time_last_update_unix")val timeLastUpdateUnix: Long,
    @field:SerializedName("time_last_update_utc")val timeLastUpdateUTC: String,
    @field:SerializedName("time_next_update_unix")val timeNextUpdateUnix: Long,
    @field:SerializedName("time_next_update_utc")val timeNextUpdateUTC: String,
    @field:SerializedName("time_eol_unix")val timeEOLUnix: Long,
    @field:SerializedName("base_code")val baseCode: String,
    @field:SerializedName("rates")val rates: Map<String, Double>
)
