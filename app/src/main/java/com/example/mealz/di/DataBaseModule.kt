package com.example.mealz.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.CategoriesDao
import com.example.data.local.MealDetailsDao
import com.example.data.local.MealsDao
import com.example.data.local.MealsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideMealsDatabase(@ApplicationContext context: Context): MealsDatabase {
        return Room.databaseBuilder(
            context,
            MealsDatabase::class.java,
            "Meals_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMealsDao(database: MealsDatabase): MealsDao {
        return database.mealsDao()
    }

    @Singleton
    @Provides
    fun provideCategoriesDao(database: MealsDatabase): CategoriesDao {
        return database.categoriesDao()
    }

    @Singleton
    @Provides
    fun provideMealDetailsDao(database: MealsDatabase): MealDetailsDao {
        return database.mealdetailsDao()
    }

}