package com.demo.weather.model.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.weather.model.api.wwo.WWOSearchCityService
import com.demo.weather.model.apidata.City
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.Dispatchers
import org.mockito.Mockito.*
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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class QueryCityRepoTest {
    val service = mock(WWOSearchCityService::class.java)
    var repo = QueryCityRepo(service)

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
    fun queryCities() = runBlockingTest {
        lenient().`when`(service.queryCities(com.nhaarman.mockitokotlin2.any())).thenReturn(
            listOf(
                City("City 1", 0),
                City("City 2", 2)
            )
        )
        repo.queryCities("test 1", object : OpenrationListener {
            override fun onSuccess(obj: Any?) {
                val list = obj as List<City>
                assertFalse(list.isNullOrEmpty())
                assertEquals(list.size, 2)
                print("Get list size ${list.size}")
            }

            override fun onError(obj: Any?) {
                fail()
            }

        })
        delay(1_000)
    }
}