package com.demo.weather.model.api

import com.demo.weather.model.api.weatherapi.WApiLocalWeatherService
import com.demo.weather.model.api.wwo.WWOLocalWeatherService
import org.junit.Test

import org.junit.Assert.*

class LocalWeatherServiceTest {
    val api = WApiLocalWeatherService()

    @Test
    fun currentWeather() {
        val weather = api.currentWeather("London")
        assertFalse(weather.humidity.isNullOrEmpty())
        assertFalse(weather.weatherIconUrl.isNullOrEmpty())
        assertFalse(weather.weatherDesc.isNullOrEmpty())
        assertFalse(weather.temp_C.isNullOrEmpty())
        assertFalse(weather.last_updated.isNullOrEmpty())
        println(weather)
    }
}