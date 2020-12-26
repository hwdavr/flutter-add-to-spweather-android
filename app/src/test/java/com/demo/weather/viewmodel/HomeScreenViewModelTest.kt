package com.demo.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.weather.model.apidata.City
import com.demo.weather.model.repository.QueryCityRepo
import com.demo.weather.model.repository.RecentCityRepo
import com.demo.weather.model.util.OpenrationListener
import com.nhaarman.mockitokotlin2.doAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeScreenViewModelTest {
    private lateinit var storageRepo: RecentCityRepo
    private lateinit var  queryRepo: QueryCityRepo
    private lateinit var  viewModel: HomeScreenViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
        storageRepo = Mockito.mock(RecentCityRepo::class.java)
        queryRepo = Mockito.mock(QueryCityRepo::class.java)
        viewModel = HomeScreenViewModel(storageRepo, queryRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun loadCityList() = runBlockingTest {
        doAnswer {
            val listener = it.arguments[0] as OpenrationListener
            listener.onSuccess(
                listOf(
                    City("City 1", 0),
                    City("City 2", 2)
                )
            )
        }.`when`(storageRepo).getCities(com.nhaarman.mockitokotlin2.any())

        viewModel.loadCityList()

        delay(1_000)
        assertTrue(viewModel._cities.value?.size ?: 0 > 0)
        print("Get list size ${viewModel._cities.value?.size}")
    }

    @Test
    fun queryCityList() = runBlockingTest {
        doAnswer { invocationOnMock ->
            val listener = invocationOnMock.arguments[1] as OpenrationListener
            listener.onSuccess(
                listOf(
                    City("City 1", 0),
                    City("City 2", 2)
                )
            )
        }.`when`(queryRepo).queryCities(ArgumentMatchers.anyString(), com.nhaarman.mockitokotlin2.any())

        viewModel.queryCityList("Test")

        delay(1_000)
        assertTrue(viewModel._cities.value?.size ?: 0 > 0)
        print("Get list size ${viewModel._cities.value?.size}")
    }
}
