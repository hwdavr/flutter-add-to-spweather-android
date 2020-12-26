package com.demo.weather.model.api.weatherapi

import com.demo.weather.model.api.HttpConnection
import com.demo.weather.model.api.ISearchCityService
import com.demo.weather.model.apidata.City
import com.demo.weather.model.apidata.weatherapi.WApiCityResult
import com.demo.weather.model.util.WEATHER_API_SEARCH_URL
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonDecodingException
import java.net.URL
import javax.inject.Inject

class WApiSearchCityService @Inject constructor() : ISearchCityService {
    private val TAG = WApiSearchCityService::class.java.simpleName

    override fun queryCities(query: String): List<City> {
        val cities = mutableListOf<City>()
        val url = URL("$WEATHER_API_SEARCH_URL&q=$query")
        val result = HttpConnection.getRequest(url) ?: return cities
        try {
            val json = Json(JsonConfiguration.Stable)
            val searchResults = json.parse(ArrayListSerializer(WApiCityResult.serializer()), result)
            for (entry in searchResults) {
                cities.add(City(entry.name, 0))
            }
        } catch (e: JsonDecodingException) {
            e.printStackTrace()
        }
        return cities
    }
}