package com.demo.weather.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.model.apidata.CurrentCondition
import com.demo.weather.model.repository.CityWeatherRepo
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityWeatherViewModel  @Inject
constructor(
    private val repository: CityWeatherRepo
): ViewModel() {
    private val TAG = CityWeatherViewModel::class.java.simpleName
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val weatherVisibility: MutableLiveData<Int> = MutableLiveData()

    val weatherIconUrlLiveData: MutableLiveData<String> = MutableLiveData()
    val temperatureLiveData: MutableLiveData<String> = MutableLiveData()
    val weatherDescLiveData: MutableLiveData<String> = MutableLiveData()
    val humidityLiveData: MutableLiveData<String> = MutableLiveData()
    val cityLiveData: MutableLiveData<String> = MutableLiveData()
    val updatedTimeLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        Log.d(TAG, "Injection discovered")
        loadingVisibility.value = View.VISIBLE
        weatherVisibility.value = View.INVISIBLE
    }

    fun checkCurrentWeather(city: String) {
        loadingVisibility.value = View.VISIBLE
        weatherVisibility.value = View.INVISIBLE
        cityLiveData.value = city
        repository.currentWeather(city, object : OpenrationListener {
            override fun onSuccess(obj: Any?) {
                viewModelScope.launch(Dispatchers.Main) {
                    if (obj != null && obj is CurrentCondition) {
                        temperatureLiveData.value = obj.temp_C
                        weatherDescLiveData.value = obj.weatherDesc
                        humidityLiveData.value = "Humidity: ${obj.humidity}"
                        updatedTimeLiveData.value = "Last updated: ${obj.last_updated}"
                        weatherIconUrlLiveData.value = obj.weatherIconUrl
                    }
                    loadingVisibility.value = View.GONE
                    weatherVisibility.value = View.VISIBLE
                }
            }

            override fun onError(obj: Any?) {
                viewModelScope.launch(Dispatchers.Main) {
                    loadingVisibility.value = View.GONE
                    weatherVisibility.value = View.VISIBLE
                }
            }
        })
    }
}
