package com.demo.weather.model.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.weather.model.apidata.City

@Database(entities = [City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}