package com.demo.weather.model.api

import com.demo.weather.model.apidata.CurrentCondition

interface ILocalWeatherService {
    fun currentWeather(query: String): CurrentCondition
}