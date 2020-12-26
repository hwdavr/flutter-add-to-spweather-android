package com.demo.weather.model.api.wwo

import com.demo.weather.model.api.HttpConnection
import com.demo.weather.model.api.ILocalWeatherService
import com.demo.weather.model.apidata.CurrentCondition
import com.demo.weather.model.util.WWO_LOCAL_WEATHER_URL
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import java.net.URL
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import javax.inject.Inject


class WWOLocalWeatherService  @Inject constructor() : ILocalWeatherService {
    private val TAG = WWOLocalWeatherService::class.java.simpleName

    override fun currentWeather(query: String): CurrentCondition {
        val weather = CurrentCondition()
        val url = URL("$WWO_LOCAL_WEATHER_URL&q=$query")
        val result = HttpConnection.getRequest(url) ?: return weather
        try {
            val mapper = ObjectMapper()
            val node = mapper.readTree(result) as? ObjectNode
            val dataNode = node?.get("data")
            val currentCondition = dataNode?.get("current_condition")?.get(0)
            weather.last_updated = currentCondition?.get("last_updated")?.textValue()
            weather.temp_C = currentCondition?.get("temp_C")?.textValue()
            weather.humidity = currentCondition?.get("humidity")?.textValue()
            val weatherIconJson = currentCondition?.get("weatherIconUrl")
            weather.weatherIconUrl = parseValue(weatherIconJson)
            val weatherDescJson = currentCondition?.get("weatherDesc")
            weather.weatherDesc = parseValue(weatherDescJson)
        } catch (e: JsonMappingException) {
            e.printStackTrace()
        }
        return weather
    }

    private fun parseValue(node: JsonNode?): String? {
        val resultValue = node?.get(0)
        return resultValue?.get("value")?.textValue()
    }
}