package com.demo.weather.model.repository

import android.util.Log
import com.demo.weather.model.api.ISearchCityService
import com.demo.weather.model.api.wwo.WWOSearchCityService
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryCityRepo @Inject
constructor(private val service: ISearchCityService) {
    private val TAG = QueryCityRepo::class.java.simpleName
    private val job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.IO + job)

    // Create another constructor to be able to test the corouine in MainScope() if doing instrumentation test
    constructor(scope: CoroutineScope, service: WWOSearchCityService): this(service) {
        this.scope = scope
    }

    init {
        Log.d(TAG, "Injection discovered")
    }

    fun queryCities(query: String, listner: OpenrationListener) {
        scope.launch {
            runBlocking {
                val cities = service.queryCities(query)
                listner.onSuccess(cities)
            }
        }
    }

}