package com.demo.weather.model.apidata.wwo

import kotlinx.serialization.Serializable

@Serializable
data class SearchApiResult(
    val result: List<CityResult>
)