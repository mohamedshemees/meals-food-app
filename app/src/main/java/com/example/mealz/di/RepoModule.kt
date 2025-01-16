package com.example.mealz.di

import com.example.data.local.CategoriesDao
import com.example.data.local.MealDetailsDao
import com.example.data.local.MealsDao
import com.example.data.remote.ApiService
import com.example.data.local.PrefsHelper
import com.example.data.repo.MealsRepoImpl
import com.example.domain.repo.MealsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(
        apiService: ApiService,
        mealsDao: MealsDao,
        categoriesDao: CategoriesDao,
        mealDetailsDao:MealDetailsDao,
        prefsHelper: PrefsHelper
        ):MealsRepo{
        return MealsRepoImpl(apiService,mealsDao,categoriesDao, mealDetailsDao,prefsHelper)
    }

}