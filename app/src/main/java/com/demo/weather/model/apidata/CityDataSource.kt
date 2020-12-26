package com.demo.weather.model.apidata

import com.demo.weather.model.apidata.City
import com.demo.weather.model.util.OpenrationListener

interface CityDataSource {

    fun insertCity(city: City)
    fun getCities(listner: OpenrationListener)
}