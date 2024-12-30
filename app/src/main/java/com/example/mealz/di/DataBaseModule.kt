package com.example.mealz.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.CategoriesDao
import com.example.data.local.CategoriesDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): CategoriesDatabase {
        return Room.databaseBuilder(
            context,
            CategoriesDatabase::class.java,
            "Category_database"
        ).build()
    }
    @Singleton
    @Provides
    fun provideDao(database:CategoriesDatabase):CategoriesDao{
        return database.categoriesDao()
    }

}