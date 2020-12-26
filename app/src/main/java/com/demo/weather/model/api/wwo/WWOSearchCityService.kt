package com.demo.weather.model.api.wwo

import com.demo.weather.model.api.HttpConnection
import com.demo.weather.model.api.ISearchCityService
import com.demo.weather.model.apidata.wwo.SearchApiResponse
import com.demo.weather.model.apidata.City
import com.demo.weather.model.util.WWO_SEARCH_URL
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonDecodingException
import java.net.URL
import javax.inject.Inject

class WWOSearchCityService @Inject constructor() : ISearchCityService {
    private val TAG = WWOSearchCityService::class.java.simpleName

    override fun queryCities(query: String): List<City> {
        val cities = mutableListOf<City>()
        val url = URL("$WWO_SEARCH_URL&q=$query")
        val result = HttpConnection.getRequest(url) ?: return cities
        try {
            val json = Json(JsonConfiguration.Stable)
            val searchResults = json.parse(SearchApiResponse.serializer(), result)
            for (entry in searchResults.search_api.result) {
                cities.add(City(entry.areaName[0].value, 0))
            }
        } catch (e: JsonDecodingException) {
            e.printStackTrace()
        }
        return cities
    }
}