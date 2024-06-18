package com.example.sunrisesunset.api

import com.google.gson.annotations.SerializedName

data class SunriseSunsetResponse(
    @SerializedName("results")
    val results: Results,
    @SerializedName("status")
    val status: String
)

data class Results(
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("first_light")
    val firstLight: String,
    @SerializedName("last_light")
    val lastLight: String,
    @SerializedName("dawn")
    val dawn: String,
    @SerializedName("dusk")
    val dusk: String,
    @SerializedName("solar_noon")
    val solarNoon: String,
    @SerializedName("golden_hour")
    val goldenHour: String,
    @SerializedName("day_length")
    val dayLength: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("utc_offset")
    val utcOffset: Int,
    @SerializedName("date")
    val date: String
)