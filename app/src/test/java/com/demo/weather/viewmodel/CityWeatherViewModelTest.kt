package com.demo.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.weather.model.apidata.CurrentCondition
import com.demo.weather.model.repository.CityWeatherRepo
import com.demo.weather.model.util.OpenrationListener
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CityWeatherViewModelTest {
    private lateinit var repository: CityWeatherRepo
    private lateinit var viewModel: CityWeatherViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = Mockito.mock(CityWeatherRepo::class.java)
        viewModel = CityWeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun checkCurrentWeather() = runBlockingTest {
        val mockCity = "Singapore"
        val mockTime = "00:00"
        val mockTemp = "0"
        val mockIconUrl = "http://"
        val mockWeather = "Cloudy"
        val mockHumidity = "80"
        doAnswer {
            val listener = it.arguments[1] as OpenrationListener
            listener.onSuccess(
                CurrentCondition().apply {
                    last_updated = mockTime
                    temp_C = mockTemp
                    weatherIconUrl = mockIconUrl
                    weatherDesc = mockWeather
                    humidity = mockHumidity
                }
            )
        }.`when`(repository).currentWeather(anyString(), any())

        viewModel.checkCurrentWeather(mockCity)

        delay(1_000)
        assertEquals(viewModel.cityLiveData.value, mockCity)
        assertEquals(viewModel.updatedTimeLiveData.value, "Last updated: $mockTime")
        assertEquals(viewModel.temperatureLiveData.value, mockTemp)
        assertEquals(viewModel.weatherIconUrlLiveData.value, mockIconUrl)
        assertEquals(viewModel.weatherDescLiveData.value, mockWeather)
        assertEquals(viewModel.humidityLiveData.value, "Humidity: $mockHumidity")

    }
}