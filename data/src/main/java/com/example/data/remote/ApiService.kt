package com.example.data.remote

import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.MealDetailsResponse
import com.example.domain.entity.Meals
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsOfCategory(@Query("c") category: String): Meals

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): MealDetailsResponse

}