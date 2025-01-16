package com.example.data.repo

import android.content.Context

import android.util.Log
import com.example.data.local.CategoriesDao
import com.example.data.local.MealDetailsDao
import com.example.data.local.MealsDao
import com.example.data.remote.ApiService
import com.example.data.local.PrefsHelper
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import com.example.domain.repo.MealsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class MealsRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val mealsDao: MealsDao,
    private val categoriesDao: CategoriesDao,
    private val mealDetailsDao: MealDetailsDao,
    private val prefsHelper: PrefsHelper,

    ) : MealsRepo {


    private lateinit var categories: List<Category>

    override suspend fun refreshCategories() {

        val lastCategoryId = 14
        prefsHelper.isInitCategorysComplete = categoriesDao.getCategoryCount() > lastCategoryId
        if (prefsHelper.isInitCategorysComplete) {
            categories = categoriesDao.getCategoriesFromLocal().firstOrNull()!!

            return
        }
        try {
            val apiCategoriesResponse = apiService.getCategories()
            categories = apiCategoriesResponse.categories

            categoriesDao.insertCategories(categories)
            prefsHelper.isInitCategorysComplete = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    lateinit var meals: List<Meal>
    lateinit var mealsWithCategory: List<Meal>
    override suspend fun refreshMeals() {
        val mealsCount = 303
        val mealsFromLocal = mealsDao.getMealsCount()
        prefsHelper.isInitMealsComplete = mealsFromLocal > mealsCount
        if (prefsHelper.isInitMealsComplete) {
            return
        }
        try {
            if (categories.isNotEmpty()) {
                for (category in categories) {
                    val apiMealsResponse = apiService.getMealsOfCategory(category.strCategory)
                    meals = apiMealsResponse.meals

                    mealsWithCategory = meals.map { meal ->
                        Meal(
                            idMeal = meal.idMeal,
                            strMeal = meal.strMeal,
                            strMealThumb = meal.strMealThumb,
                            categoryId = category.strCategory
                        )
                    }
                    mealsDao.insertMeals(mealsWithCategory)
                    refreshMealsDetails(mealsWithCategory)
                    prefsHelper.isInitMealsComplete = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun refreshMealsDetails(mealsWithCategory: List<Meal>) {
        val mealsCount = 303
        val mealsFromLocal = mealDetailsDao.getMealsDetailsCount()
        prefsHelper.isInitMealsDetailsComplete = mealsFromLocal > mealsCount
        if (prefsHelper.isInitMealsDetailsComplete) {
            return
        }
        for (meal in mealsWithCategory) {
            try {
                val mealDetails = apiService.getMealDetails(meal.idMeal)
                mealDetailsDao.insertMealDetails(mealDetails.meals)

            } catch (e: Exception) {
                Log.d("wow", "inside refrecategories $e")
            }
        }
        prefsHelper.isInitMealsDetailsComplete = true
    }

    override suspend fun refreshData(context: Context) {
        prefsHelper.DataComplete =
            (prefsHelper.isInitCategorysComplete && prefsHelper.isInitMealsComplete && prefsHelper.isInitMealsDetailsComplete)
        Log.d("wow", "inside refreshdata")
        if (prefsHelper.DataComplete) {
            return
        } else {
            refreshCategories()
            refreshMeals()
        }
        prefsHelper.DataComplete = true
    }

    override fun getCategories(): Flow<List<Category>> {
        return categoriesDao.getCategoriesFromLocal()
    }

    override fun getMeals(category: String): Flow<List<Meal>> {
        return mealsDao.getMealsFromLocal(category)
    }

}


