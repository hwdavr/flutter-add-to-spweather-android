package com.demo.weather.model.apidata.weatherapi

import kotlinx.serialization.Serializable

@Serializable
data class WApiCityResult(
    val id: Int,
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    val url: String
)