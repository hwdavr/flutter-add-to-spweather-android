package com.demo.weather.model.city

import android.content.Context
import androidx.room.Room
import com.demo.weather.model.database.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import androidx.test.core.app.ApplicationProvider
import com.demo.weather.model.apidata.City
import com.demo.weather.model.dao.CityDao
import junit.framework.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CityDaoTest {
    private lateinit var cityDao: CityDao
    private lateinit var db: AppDatabase
    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        cityDao = db.cityDao()
    }

    @After
    fun closeDB() {
        db.close()
    }


    @Test
    fun insertAndGetCities() {
        cityDao.insert(City("City 1", 0))
        cityDao.insert(City("City 2", 1))
        cityDao.insert(City("City 3", 4))
        cityDao.insert(City("City 4", 3))
        cityDao.insert(City("City 5", 4))
        cityDao.insert(City("City 6", 10))
        cityDao.insert(City("City 7", 4))
        cityDao.insert(City("City 8", 12))
        cityDao.insert(City("City 9", 5))
        cityDao.insert(City("City 10", 6))
        cityDao.insert(City("City 11", 19))

        val list = cityDao.latest
        assertFalse(list.isNullOrEmpty())
        assertEquals(list.size, 10)
        assertEquals(list[0].name, "City 11")
        print("City list size: ${list.size}")
    }
}