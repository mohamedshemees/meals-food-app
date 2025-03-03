package com.example.domain.repo

import android.content.Context
import android.graphics.Bitmap
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import com.example.domain.entity.MealDetails
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface MealsRepo {


    fun getMeals(category: String): Flow<List<Meal>>

    fun getCategories(): Flow<List<Category>>

    suspend fun refreshCategories()

    suspend fun refreshMeals()

    suspend fun refreshMealsDetails(mealsWithCategory: List<Meal>)

    suspend fun refreshData(context: Context)

    suspend fun getIngredients(meal: String): MealDetails

    fun getFavorites(): Flow<List<Meal>>

    suspend fun toggleFavoriteStatus(mealId: String, isFavorite: Boolean)

    suspend fun getFavState(mealId: String): Boolean

     suspend fun getIngThumbnail(ingredient: String?): Bitmap?


    suspend fun downloadAndSaveImage(context: Context, imageUrl: String, fileName: String): String?


}