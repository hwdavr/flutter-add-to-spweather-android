package com.demo.weather.model.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.weather.model.apidata.City
import com.demo.weather.model.dao.CityDao
import com.demo.weather.model.util.OpenrationListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecentCityRepoTest {
    // Init with Main Coroutine Scope
    var dao: CityDao = mock(CityDao::class.java)
    var repo = RecentCityRepo(dao)
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
    fun insert() = runBlockingTest {
        // insert
        repo.insertCity(City("Test 1", 4))
        repo.insertCity(City("Test 2", 5))

        delay(1_000)
        verify(dao, times(2)).upsert(com.nhaarman.mockitokotlin2.any())
    }

    @Test
    fun getCities() = runBlockingTest {
        Mockito.lenient().`when`(dao.latest).thenReturn(
            listOf(
                City("City 1", 0),
                City("City 2", 2)
            )
        )
        // retrieve
        repo.getCities(object : OpenrationListener {
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