package com.demo.weather.model.repository

import android.util.Log
import com.demo.weather.model.api.ILocalWeatherService
import com.demo.weather.model.api.wwo.WWOLocalWeatherService
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityWeatherRepo @Inject
constructor(private val service: ILocalWeatherService) {
    private val TAG = CityWeatherRepo::class.java.simpleName
    private val job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.IO + job)

    // Create another constructor to be able to test the corouine in MainScope() if doing instrumentation test
    constructor(scope: CoroutineScope, service: WWOLocalWeatherService): this(service) {
        this.scope = scope
    }

    init {
        Log.d(TAG, "Injection discovered")
    }

    fun currentWeather(query: String, listner: OpenrationListener) {
        scope.launch {
            runBlocking {
                val weather = service.currentWeather(query)
                listner.onSuccess(weather)
            }
        }
    }

}