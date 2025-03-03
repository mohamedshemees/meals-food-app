package com.example.domain.usecase

import android.graphics.Bitmap
import com.example.domain.entity.Meal
import com.example.domain.entity.MealDetails

import com.example.domain.repo.MealsRepo
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

class GetMeals(
    private val mealsrepo: MealsRepo

) {
    fun getMeals(category: String): Flow<List<Meal>> {
        return mealsrepo.getMeals(category)
    }

    fun getFavoriteMeals(): Flow<List<Meal>> {
        return mealsrepo.getFavorites()
    }

    suspend fun getingredients(meal: String): MealDetails {
        return mealsrepo.getIngredients(meal)
    }

    suspend fun toggleFavoriteStatus(mealId: String, isFavorite: Boolean) {
        mealsrepo.toggleFavoriteStatus(mealId, isFavorite)
    }
    suspend fun getFavState(mealId: String): Boolean{
        return mealsrepo.getFavState(mealId)
    }

    suspend fun getIngThumbnail(ingredient: String?): Bitmap? {
        return mealsrepo.getIngThumbnail(ingredient)
    }


}
