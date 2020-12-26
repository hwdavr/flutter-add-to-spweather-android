package com.demo.weather.model.api.weatherapi

import com.demo.weather.model.api.HttpConnection
import com.demo.weather.model.api.ILocalWeatherService
import com.demo.weather.model.apidata.CurrentCondition
import com.demo.weather.model.util.WEATHER_API_CURRENT_WEATHER_URL
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.net.URL
import javax.inject.Inject

class WApiLocalWeatherService  @Inject constructor() : ILocalWeatherService {
    private val TAG = WApiLocalWeatherService::class.java.simpleName

    override fun currentWeather(query: String): CurrentCondition {
        val weather = CurrentCondition()
        val url = URL("$WEATHER_API_CURRENT_WEATHER_URL&q=$query")
        val result = HttpConnection.getRequest(url) ?: return weather
        try {
            val mapper = ObjectMapper()
            val node = mapper.readTree(result) as? ObjectNode
            val dataNode = node?.get("data")
            val currentCondition = node?.get("current")
            weather.last_updated = currentCondition?.get("last_updated")?.textValue()
            weather.temp_C = currentCondition?.get("temp_c")?.floatValue().toString()
            weather.humidity = currentCondition?.get("humidity")?.intValue().toString()
            val conditionNode = currentCondition?.get("condition")
            weather.weatherIconUrl = "http:" + conditionNode?.get("icon")?.textValue()
            weather.weatherDesc = conditionNode?.get("text")?.textValue()
        } catch (e: JsonMappingException) {
            e.printStackTrace()
        }
        return weather
    }
}