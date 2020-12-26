package com.demo.weather.model.repository

import android.util.Log
import com.demo.weather.model.apidata.City
import com.demo.weather.model.apidata.CityDataSource
import com.demo.weather.model.dao.CityDao
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentCityRepo @Inject
constructor(val cityDao: CityDao) : CityDataSource {
    private val TAG = RecentCityRepo::class.java.simpleName
    private val job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.IO + job)

    init {
        Log.d(TAG, "Injection discovered")
    }

    // Create another constructor to be able to test the corouine in MainScope() if doing instrumentation test
    constructor(scope: CoroutineScope, dao: CityDao): this(dao) {
        this.scope = scope
    }

    override fun insertCity(city: City) {
        scope.launch {
            runBlocking {
                cityDao.upsert(city)
            }
        }
    }

    override fun getCities(listner: OpenrationListener) {
        scope.launch {
            runBlocking {
                listner.onSuccess(cityDao.latest)
            }
        }
    }


}