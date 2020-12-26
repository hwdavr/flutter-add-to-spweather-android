package com.demo.weather.model.api

import com.demo.weather.model.apidata.City

interface ISearchCityService {
    fun queryCities(query: String): List<City>
}