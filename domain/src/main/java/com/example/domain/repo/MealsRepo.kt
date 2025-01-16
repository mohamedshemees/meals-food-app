package com.example.domain.repo

import android.content.Context
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import kotlinx.coroutines.flow.Flow

interface MealsRepo {

    fun getMeals(category: String): Flow<List<Meal>>

     fun getCategories(): Flow<List<Category>>

    suspend fun refreshCategories()

    suspend fun refreshMeals()

    suspend fun refreshMealsDetails(mealsWithCategory: List<Meal>)

    suspend fun refreshData(context: Context)
}