package com.demo.weather.model.apidata.wwo

import com.demo.weather.model.apidata.wwo.SearchApiResult
import kotlinx.serialization.*

@Serializable
data class SearchApiResponse(
    val search_api: SearchApiResult
)
