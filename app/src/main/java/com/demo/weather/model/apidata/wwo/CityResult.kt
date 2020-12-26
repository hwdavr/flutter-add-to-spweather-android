package com.demo.weather.model.apidata.wwo

import kotlinx.serialization.Serializable

@Serializable
data class CityResult(
    val areaName: List<ResultValue>,
    val country: List<ResultValue>,
    val region: List<ResultValue>,
    val latitude: String,
    val longitude: String,
    val population: String,
    val weatherUrl: List<ResultValue>
)