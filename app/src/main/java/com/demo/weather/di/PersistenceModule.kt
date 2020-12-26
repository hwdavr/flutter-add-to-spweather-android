package com.demo.weather.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.demo.weather.model.dao.CityDao
import com.demo.weather.model.database.AppDatabase
import com.demo.weather.model.repository.RecentCityRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "Cities.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideCityDao(@NonNull database: AppDatabase): CityDao {
        return database.cityDao()
    }

    @Provides
    @Singleton
    fun provideRecentCityRepo(@NonNull cityDao: CityDao): RecentCityRepo {
        return RecentCityRepo(cityDao)
    }
}