package com.demo.weather.model.dao

import com.demo.weather.model.apidata.City
import androidx.room.*


@Dao
interface CityDao {
    @get:Query("SELECT * FROM city ORDER BY updated_at DESC LIMIT 10")
    val latest: List<City>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg cities: City): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(city: City): Long

    @Query("UPDATE city SET updated_at = :updatedAt WHERE city_name = :name")
    fun update(name: String, updatedAt: Long)

    @Transaction
    fun upsert(city: City) {
        val id = insert(city)
        if (id == (-1).toLong()) {
            update(city.name, city.updatedAt)
        }
    }
}