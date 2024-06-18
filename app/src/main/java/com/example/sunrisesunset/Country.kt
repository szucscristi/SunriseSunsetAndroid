package com.example.sunrisesunset

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val key: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
)