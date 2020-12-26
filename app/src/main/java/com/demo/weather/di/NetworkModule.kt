package com.demo.weather.di

import com.demo.weather.model.api.ILocalWeatherService
import com.demo.weather.model.api.ISearchCityService
import com.demo.weather.model.api.weatherapi.WApiLocalWeatherService
import com.demo.weather.model.api.weatherapi.WApiSearchCityService
import com.demo.weather.model.api.wwo.WWOLocalWeatherService
import com.demo.weather.model.api.wwo.WWOSearchCityService
import com.demo.weather.model.util.API_SELECTION
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideSearchService(): ISearchCityService {
        if (API_SELECTION == 0) {
            return WWOSearchCityService()
        } else {
            return WApiSearchCityService()
        }
    }


    @Provides
    @Singleton
    fun provideCityWeatherPepo(): ILocalWeatherService {
        if (API_SELECTION == 0) {
            return WWOLocalWeatherService()
        } else {
            return WApiLocalWeatherService()
        }
    }
}