package com.example.sunrisesunset.api

import com.example.sunrisesunset.api.Results
import com.google.gson.annotations.SerializedName

data class SunriseSunsetRangeResponse(
    @SerializedName("results")
    val resultsList: List<Results>,
    @SerializedName("status")
    val status: String
)
