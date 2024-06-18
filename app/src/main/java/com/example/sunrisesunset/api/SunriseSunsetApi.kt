package com.example.sunrisesunset

import com.example.sunrisesunset.api.SunriseSunsetRangeResponse
import com.example.sunrisesunset.api.SunriseSunsetResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SunriseSunsetApi {
    @GET("json")
    suspend fun getSunriseSunset(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("date") date: String? = null
    ): SunriseSunsetResponse




    @GET("json")
    suspend fun getSunriseSunset(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("date_start") startDate: String,
        @Query("date_end") endDate: String
    ): SunriseSunsetRangeResponse



    companion object {
        private const val BASE_URL = "https://api.sunrisesunset.io/"

        fun create(): SunriseSunsetApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(SunriseSunsetApi::class.java)
        }
    }
}