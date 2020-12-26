package com.demo.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.weather.model.apidata.City
import com.demo.weather.model.repository.QueryCityRepo
import com.demo.weather.model.repository.RecentCityRepo
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeScreenViewModel @Inject
constructor(
    private val storageRepo: RecentCityRepo,
    private val queryRepo: QueryCityRepo
): ViewModel() {

    private val TAG = HomeScreenViewModel::class.java.simpleName
    val cities: MutableLiveData<List<City>> = MutableLiveData<List<City>>().apply { value = emptyList() }

    init {
        Log.d(TAG, "Injection discovered")
    }

    fun insertCity(name: String) {
        val timestamp = System.currentTimeMillis()
        storageRepo.insertCity(City(name, timestamp))
    }

    fun clearCityList() {
        cities.value = emptyList()
    }

    fun loadCityList() {
        storageRepo.getCities(object : OpenrationListener{
            override fun onSuccess(obj: Any?) {
                onOperationSucceed(obj)
            }

            override fun onError(obj: Any?) {
            }
        })
    }


    fun queryCityList(query: String) {
        queryRepo.queryCities(query, object : OpenrationListener {
            override fun onSuccess(obj: Any?) {
                onOperationSucceed(obj)
            }

            override fun onError(obj: Any?) {
            }
        })
    }

    private fun onOperationSucceed(obj: Any?) {
        if (obj != null && obj is List<*>) {
            GlobalScope.launch(Dispatchers.Main) {
                cities.value = obj as List<City>
            }
        }
    }
}