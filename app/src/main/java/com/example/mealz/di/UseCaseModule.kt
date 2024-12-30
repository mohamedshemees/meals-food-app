package com.example.mealz.di

import com.example.domain.repo.CategoriesRepo
import com.example.domain.usecase.GetCategories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(
        categoriesRepo: CategoriesRepo): GetCategories{
        return GetCategories(categoriesRepo)
    }
}