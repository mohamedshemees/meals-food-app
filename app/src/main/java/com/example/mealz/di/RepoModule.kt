package com.example.mealz.di

import com.example.data.local.CategoriesDao
import com.example.data.remote.ApiService
import com.example.data.repo.CategoriesRepoImpl
import com.example.domain.repo.CategoriesRepo
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
        categoriesDao: CategoriesDao):CategoriesRepo{
        return CategoriesRepoImpl(apiService,categoriesDao)
    }
}