package com.demo.weather.model.util

internal const val CITY_ID = "city"

// 0: WWO, 1: Weather API
internal const val API_SELECTION = 1

internal const val WWO_API_KEY = "f03589d98f1646cbb99102026201601"
internal const val WWO_SEARCH_URL = "https://api.worldweatheronline.com/premium/v1/search.ashx?key=$WWO_API_KEY&format=json"
internal const val WWO_LOCAL_WEATHER_URL = "https://api.worldweatheronline.com/premium/v1/weather.ashx?key=$WWO_API_KEY&format=json"


internal const val WEATHER_API_KEY = "91a79274bd7c4262aab134853202103"
internal const val WEATHER_API_SEARCH_URL = "http://api.weatherapi.com/v1/search.json?key=$WEATHER_API_KEY"
internal const val WEATHER_API_CURRENT_WEATHER_URL = "http://api.weatherapi.com/v1/current.json?key=$WEATHER_API_KEY"