package com.demo.weather.model.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.weather.model.api.wwo.WWOLocalWeatherService
import com.demo.weather.model.apidata.CurrentCondition
import com.demo.weather.model.util.OpenrationListener
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.Dispatchers
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
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CityWeatherRepoTest {
    private val service = mock(WWOLocalWeatherService::class.java)
    private val repo = CityWeatherRepo(service)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun currentWeather() = runBlockingTest {
        val mockCity = "Singapore"
        val mockTime = "00:00"
        val mockTemp = "0"
        val mockIconUrl = "http://"
        val mockWeather = "Cloudy"
        val mockHumidity = "80"
        val currentCondition = CurrentCondition().apply {
            last_updated = mockTime
            temp_C = mockTemp
            weatherIconUrl = mockIconUrl
            weatherDesc = mockWeather
            humidity = mockHumidity
        }
        Mockito.lenient().`when`(service.currentWeather(any())).thenReturn(currentCondition)

        repo.currentWeather(mockCity, object : OpenrationListener {
            override fun onSuccess(obj: Any?) {
                assertEquals(obj, currentCondition)
            }

            override fun onError(obj: Any?) {
                fail()
            }

        })
    }
}