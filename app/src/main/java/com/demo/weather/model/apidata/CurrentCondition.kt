package com.demo.weather.model.apidata

import kotlinx.serialization.Serializable

@Serializable
data class CurrentCondition(
    var last_updated: String? = null,
    var temp_C: String? = null,
    var weatherIconUrl: String? = null,
    var weatherDesc: String? = null,
    var humidity: String? = null
)